package cn.neud.trace.note;

import cn.neud.trace.note.model.entity.Trace;
import cn.neud.trace.note.service.impl.TraceServiceImpl;
import cn.neud.trace.note.utils.CacheTemplate;
import cn.neud.trace.note.utils.RedisIdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cn.neud.trace.note.constant.RedisConstant.CACHE_TRACE_KEY;
import static cn.neud.trace.note.constant.RedisConstant.TRACE_GEO_KEY;

@SpringBootTest
class TraceNoteApplicationTests {

    @Resource
    private CacheTemplate cacheTemplate;

    @Resource
    private TraceServiceImpl traceService;

    @Resource
    private RedisIdWorker redisIdWorker;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private ExecutorService es = Executors.newFixedThreadPool(500);

    @Test
    void testIdWorker() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(300);

        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                long id = redisIdWorker.nextId("order");
                System.out.println("id = " + id);
            }
            latch.countDown();
        };
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 300; i++) {
            es.submit(task);
        }
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("time = " + (end - begin));
    }

    @Test
    void testSaveTrace() throws InterruptedException {
        Trace trace = traceService.getById(1L);
        cacheTemplate.setWithLogicalExpire(CACHE_TRACE_KEY + 1L, trace, 10L, TimeUnit.SECONDS);
    }

    @Test
    void loadTraceData() {
        // 1.查询场景信息
        List<Trace> list = traceService.list();
        // 2.把场景分组，按照typeId分组，typeId一致的放到一个集合
        Map<Long, List<Trace>> map = list.stream().collect(Collectors.groupingBy(Trace::getTypeId));
        // 3.分批完成写入Redis
        for (Map.Entry<Long, List<Trace>> entry : map.entrySet()) {
            // 3.1.获取类型id
            Long typeId = entry.getKey();
            String key = TRACE_GEO_KEY + typeId;
            // 3.2.获取同类型的场景的集合
            List<Trace> value = entry.getValue();
            List<RedisGeoCommands.GeoLocation<String>> locations = new ArrayList<>(value.size());
            // 3.3.写入redis GEOADD key 经度 纬度 member
            for (Trace trace : value) {
                // stringRedisTemplate.opsForGeo().add(key, new Point(trace.getX(), trace.getY()), trace.getId().toString());
                locations.add(new RedisGeoCommands.GeoLocation<>(
                        trace.getId().toString(),
                        new Point(trace.getX(), trace.getY())
                ));
            }
            stringRedisTemplate.opsForGeo().add(key, locations);
        }
    }

    @Test
    void testHyperLogLog() {
        String[] values = new String[1000];
        int j = 0;
        for (int i = 0; i < 1000000; i++) {
            j = i % 1000;
            values[j] = "user_" + i;
            if(j == 999){
                // 发送到Redis
                stringRedisTemplate.opsForHyperLogLog().add("hl2", values);
            }
        }
        // 统计数量
        Long count = stringRedisTemplate.opsForHyperLogLog().size("hl2");
        System.out.println("count = " + count);
    }
}
