package cn.neud.trace.note.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.neud.trace.note.service.TraceService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.neud.trace.note.model.dto.Result;
import cn.neud.trace.note.model.entity.Trace;
import cn.neud.trace.note.mapper.TraceMapper;
import cn.neud.trace.note.utils.CacheTemplate;
import cn.neud.trace.note.constant.SystemConstants;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static cn.neud.trace.note.constant.RedisConstant.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author David
 * @since 2021-12-22
 */
@Service
public class TraceServiceImpl extends ServiceImpl<TraceMapper, Trace> implements TraceService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private CacheTemplate cacheTemplate;

    @Override
    public Result queryById(Long id) {
        // 解决缓存穿透
        Trace trace = cacheTemplate
                .queryWithPassThrough(CACHE_TRACE_KEY, id, Trace.class, this::getById, CACHE_TRACE_TTL, TimeUnit.MINUTES);

        // 互斥锁解决缓存击穿
        // Trace trace = cacheClient
        //         .queryWithMutex(CACHE_TRACE_KEY, id, Trace.class, this::getById, CACHE_TRACE_TTL, TimeUnit.MINUTES);

        // 逻辑过期解决缓存击穿
        // Trace trace = cacheClient
        //         .queryWithLogicalExpire(CACHE_TRACE_KEY, id, Trace.class, this::getById, 20L, TimeUnit.SECONDS);

        if (trace == null) {
            return Result.fail("场景不存在！");
        }
        // 7.返回
        return Result.ok(trace);
    }

    @Override
    @Transactional
    public Result update(Trace trace) {
        Long id = trace.getId();
        if (id == null) {
            return Result.fail("场景id不能为空");
        }
        // 1.更新数据库
        updateById(trace);
        // 2.删除缓存
        stringRedisTemplate.delete(CACHE_TRACE_KEY + id);
        return Result.ok();
    }

    @Override
    public Result queryTraceByType(Integer typeId, Integer current, Double x, Double y) {
        // 1.判断是否需要根据坐标查询
        if (x == null || y == null) {
            // 不需要坐标查询，按数据库查询
            Page<Trace> page = query()
                    .eq("type_id", typeId)
                    .page(new Page<>(current, SystemConstants.DEFAULT_PAGE_SIZE));
            // 返回数据
            return Result.ok(page.getRecords());
        }

        // 2.计算分页参数
        int from = (current - 1) * SystemConstants.DEFAULT_PAGE_SIZE;
        int end = current * SystemConstants.DEFAULT_PAGE_SIZE;

        // 3.查询redis、按照距离排序、分页。结果：traceId、distance
        String key = TRACE_GEO_KEY + typeId;
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = stringRedisTemplate.opsForGeo()
                // GEOSEARCH key BYLONLAT x y BYRADIUS 10 WITHDISTANCE
                .search(key, GeoReference.fromCoordinate(x, y), new Distance(5000),
                        RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs().includeDistance().limit(end));
        // 4.解析出id
        if (results == null) {
            return Result.ok(Collections.emptyList());
        }
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> list = results.getContent();
        if (list.size() <= from) {
            // 没有下一页了，结束
            return Result.ok(Collections.emptyList());
        }
        // 4.1.截取 from ~ end的部分
        List<Long> ids = new ArrayList<>(list.size());
        Map<String, Distance> distanceMap = new HashMap<>(list.size());
        list.stream().skip(from).forEach(result -> {
            // 4.2.获取场景id
            String traceIdStr = result.getContent().getName();
            ids.add(Long.valueOf(traceIdStr));
            // 4.3.获取距离
            Distance distance = result.getDistance();
            distanceMap.put(traceIdStr, distance);
        });
        // 5.根据id查询Trace
        String idStr = StrUtil.join(",", ids);
        List<Trace> traces = query().in("id", ids).last("ORDER BY FIELD(id," + idStr + ")").list();
        for (Trace trace : traces) {
            trace.setDistance(distanceMap.get(trace.getId().toString()).getValue());
        }
        // 6.返回
        return Result.ok(traces);
    }
}
