package cn.neud.trace.note.controller;


import cn.neud.trace.note.model.dto.Result;
import cn.neud.trace.note.model.entity.TraceType;
import cn.neud.trace.note.service.TraceTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author David
 */
@RestController
@RequestMapping("/trace-type")
public class TraceTypeController {
    @Resource
    private TraceTypeService typeService;

    @GetMapping("list")
    public Result queryTypeList() {
        List<TraceType> typeList = typeService
                .query().orderByAsc("sort").list();
        return Result.ok(typeList);
    }
}
