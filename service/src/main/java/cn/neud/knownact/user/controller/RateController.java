package cn.neud.knownact.user.controller;

import cn.neud.knownact.common.annotation.AuthCheck;
import cn.neud.knownact.common.annotation.LogOperation;
import cn.neud.knownact.common.exception.BusinessException;
import cn.neud.knownact.common.exception.ErrorCode;
import cn.neud.knownact.model.constant.Constant;
import cn.neud.knownact.model.dto.page.PageData;
import cn.neud.knownact.common.utils.ExcelUtils;
import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.common.validator.AssertUtils;
import cn.neud.knownact.common.validator.ValidatorUtils;
import cn.neud.knownact.common.validator.group.AddGroup;
import cn.neud.knownact.common.validator.group.DefaultGroup;
import cn.neud.knownact.common.validator.group.UpdateGroup;
import cn.neud.knownact.model.dto.RateDTO;
import cn.neud.knownact.model.excel.RateExcel;
import cn.neud.knownact.user.service.FeedService;
import cn.neud.knownact.user.service.RateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-02
 */
@RestController
@RequestMapping("knownact/rate")
@Api(tags="")
public class RateController {
    @Autowired
    private RateService rateService;
    @Resource
    private FeedService feedService;

    private static final ExecutorService REC_TRAIN_EXECUTOR = Executors.newFixedThreadPool(2);

    @AuthCheck
    @GetMapping("like/{id}")
    @ApiOperation("点赞")
    // @RequiresPermissions("knownact:follow:info")
    public Result like(@PathVariable("id") Long id){
        boolean set = rateService.like(id);
        if (!set) {
            return new Result().ok("已取消点赞");
        }
        return new Result().ok("已成功点赞");
    }

    @AuthCheck
    @GetMapping("dislike/{id}")
    @ApiOperation("点踩")
    // @RequiresPermissions("knownact:follow:info")
    public Result dislike(@PathVariable("id") Long id){
        boolean set = rateService.dislike(id);
        if (!set) {
            return new Result().ok("已取消点踩");
        }
        return new Result().ok("已成功点踩");
    }

    @AuthCheck
    @GetMapping("favorite/{id}")
    @ApiOperation("收藏")
    // @RequiresPermissions("knownact:follow:info")
    public Result favorite(@PathVariable("id") Long id){
        boolean set = rateService.favorite(id);
        if (!set) {
            return new Result().ok("已取消收藏");
        }
        return new Result().ok("已成功收藏");
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    // @RequiresPermissions("knownact:rate:page")
    public Result<PageData<RateDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<RateDTO> page = rateService.page(params);

        return new Result<PageData<RateDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    // @RequiresPermissions("knownact:rate:info")
    public Result<RateDTO> get(@PathVariable("id") Long id){
        RateDTO data = rateService.get(id);

        return new Result<RateDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    // @RequiresPermissions("knownact:rate:save")
    public Result save(@RequestBody RateDTO dto) throws Exception {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        rateService.save(dto);

        REC_TRAIN_EXECUTOR.submit(() -> {
            try {
                feedService.train();
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.TRAIN_ERROR);
            }
        });

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    // @RequiresPermissions("knownact:rate:update")
    public Result update(@RequestBody RateDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        rateService.update(dto);

        REC_TRAIN_EXECUTOR.submit(() -> {
            try {
                feedService.train();
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.TRAIN_ERROR);
            }
        });

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    // @RequiresPermissions("knownact:rate:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        rateService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    // @RequiresPermissions("knownact:rate:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<RateDTO> list = rateService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, RateExcel.class);
    }

}