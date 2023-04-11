package cn.neud.trace.note.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.neud.trace.note.model.dto.Result;
import cn.neud.trace.note.model.entity.Trace;
import cn.neud.trace.note.service.TraceService;
import cn.neud.trace.note.constant.SystemConstants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author David
 */
@RestController
@RequestMapping("/trace")
public class TraceController {

    @Resource
    public TraceService traceService;

    /**
     * 根据id查询场景信息
     *
     * @param id 场景id
     * @return 场景详情数据
     */
    @GetMapping("/{id}")
    public Result queryTraceById(@PathVariable("id") Long id) {
        return traceService.queryById(id);
    }

    /**
     * 新增场景信息
     *
     * @param trace 场景数据
     * @return 场景id
     */
    @PostMapping
    public Result saveTrace(@RequestBody Trace trace) {
        // 写入数据库
        traceService.save(trace);
        // 返回场景id
        return Result.ok(trace.getId());
    }

    /**
     * 更新场景信息
     *
     * @param trace 场景数据
     * @return 无
     */
    @PutMapping
    public Result updateTrace(@RequestBody Trace trace) {
        // 写入数据库
        return traceService.update(trace);
    }

    /**
     * 根据场景类型分页查询场景信息
     *
     * @param typeId  场景类型
     * @param current 页码
     * @return 场景列表
     */
    @GetMapping("/of/type")
    public Result queryTraceByType(
            @RequestParam("typeId") Integer typeId,
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "x", required = false) Double x,
            @RequestParam(value = "y", required = false) Double y
    ) {
        return traceService.queryTraceByType(typeId, current, x, y);
    }

    /**
     * 根据场景名称关键字分页查询场景信息
     *
     * @param name    场景名称关键字
     * @param current 页码
     * @return 场景列表
     */
    @GetMapping("/of/name")
    public Result queryTraceByName(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "current", defaultValue = "1") Integer current
    ) {
        // 根据类型分页查询
        Page<Trace> page = traceService.query()
                .like(StrUtil.isNotBlank(name), "name", name)
                .page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 返回数据
        return Result.ok(page.getRecords());
    }
}
