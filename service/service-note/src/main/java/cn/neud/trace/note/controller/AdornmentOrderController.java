package cn.neud.trace.note.controller;


import cn.neud.trace.note.model.dto.Result;
import cn.neud.trace.note.service.AdornmentOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *
 * @author David
 */
@RestController
@RequestMapping("/adornment-order")
public class AdornmentOrderController {

    @Resource
    private AdornmentOrderService adornmentOrderService;

    @GetMapping("seckill/{id}")
    public Result seckillAdornment(@PathVariable("id") Long adornmentId) {
        return adornmentOrderService.seckillAdornment(adornmentId);
    }
}
