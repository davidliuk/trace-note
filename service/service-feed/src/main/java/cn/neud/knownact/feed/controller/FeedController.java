package cn.neud.knownact.feed.controller;

import cn.neud.knownact.common.annotation.LogOperation;
import cn.neud.knownact.feed.service.FeedService;
import cn.neud.knownact.model.constant.Constant;
import cn.neud.knownact.model.dto.page.PageData;
import cn.neud.knownact.common.utils.ExcelUtils;
import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.common.validator.AssertUtils;
import cn.neud.knownact.common.validator.ValidatorUtils;
import cn.neud.knownact.common.validator.group.AddGroup;
import cn.neud.knownact.common.validator.group.DefaultGroup;
import cn.neud.knownact.common.validator.group.UpdateGroup;
import cn.neud.knownact.model.dto.FeedDTO;
import cn.neud.knownact.model.excel.FeedExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
// import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@RestController
@RequestMapping("/feed")
@Api(tags="")
public class FeedController {
    @Autowired
    private FeedService feedService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    // @RequiresPermissions("knownact:feed:page")
    public Result<PageData<FeedDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<FeedDTO> page = feedService.page(params);

        return new Result<PageData<FeedDTO>>().ok(page);
    }

    @GetMapping("")
    @ApiOperation("训练")
    // @RequiresPermissions("knownact:feed:info")
    public Result<String> train() throws Exception {
        feedService.train();

        return new Result<String>().ok("训练成功");
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    // @RequiresPermissions("knownact:feed:info")
    public Result<FeedDTO> get(@PathVariable("id") Long id){
        FeedDTO data = feedService.get(id);

        return new Result<FeedDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    // @RequiresPermissions("knownact:feed:save")
    public Result save(@RequestBody FeedDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        feedService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    // @RequiresPermissions("knownact:feed:update")
    public Result update(@RequestBody FeedDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        feedService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    // @RequiresPermissions("knownact:feed:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        feedService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    // @RequiresPermissions("knownact:feed:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<FeedDTO> list = feedService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, FeedExcel.class);
    }

}