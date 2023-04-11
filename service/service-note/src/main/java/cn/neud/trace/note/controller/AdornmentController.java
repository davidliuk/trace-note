package cn.neud.trace.note.controller;


import cn.neud.trace.note.model.dto.Result;
import cn.neud.trace.note.model.entity.Adornment;
import cn.neud.trace.note.service.AdornmentService;
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
@RequestMapping("/adornment")
public class AdornmentController {

    @Resource
    private AdornmentService adornmentService;

    /**
     * 新增秒杀装扮物
     *
     * @param adornment 装扮物信息，包含秒杀信息
     * @return 装扮物id
     */
    @PostMapping("seckill")
    public Result addSeckillAdornment(@RequestBody Adornment adornment) {
        adornmentService.addSeckillAdornment(adornment);
        return Result.ok(adornment.getId());
    }

    /**
     * 新增普通装扮物
     *
     * @param adornment 装扮物信息
     * @return 装扮物id
     */
    @PostMapping
    public Result addAdornment(@RequestBody Adornment adornment) {
        adornmentService.save(adornment);
        return Result.ok(adornment.getId());
    }

    /**
     * 查询场景的装扮物列表
     *
     * @param traceId 场景id
     * @return 装扮物列表
     */
    @GetMapping("/list/{traceId}")
    public Result queryAdornmentOfTrace(@PathVariable("traceId") Long traceId) {
        return adornmentService.queryAdornmentOfTrace(traceId);
    }
}
