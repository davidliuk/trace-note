package cn.neud.knownact.user.controller;

import cn.neud.knownact.common.annotation.LogOperation;
import cn.neud.knownact.model.constant.Constant;
import cn.neud.knownact.model.dto.page.PageData;
import cn.neud.knownact.common.utils.ExcelUtils;
import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.common.validator.AssertUtils;
import cn.neud.knownact.common.validator.ValidatorUtils;
import cn.neud.knownact.common.validator.group.AddGroup;
import cn.neud.knownact.common.validator.group.DefaultGroup;
import cn.neud.knownact.common.validator.group.UpdateGroup;
import cn.neud.knownact.model.dto.FollowDTO;
import cn.neud.knownact.model.excel.FollowExcel;
import cn.neud.knownact.user.service.FeedService;
import cn.neud.knownact.user.service.FollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
// import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
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
@RequestMapping("knownact/follow")
@Api(tags="")
public class FollowController {
    @Autowired
    private FollowService followService;

    @Resource
    private FeedService feedService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    // @RequiresPermissions("knownact:follow:page")
    public Result<PageData<FollowDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<FollowDTO> page = followService.page(params);

        return new Result<PageData<FollowDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    // @RequiresPermissions("knownact:follow:info")
    public Result<FollowDTO> get(@PathVariable("id") Long id){
        FollowDTO data = followService.get(id);

        return new Result<FollowDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    // @RequiresPermissions("knownact:follow:save")
    public Result save(@RequestBody FollowDTO dto) throws Exception {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        followService.save(dto);
        feedService.train();
        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    // @RequiresPermissions("knownact:follow:update")
    public Result update(@RequestBody FollowDTO dto) throws Exception {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        followService.update(dto);
        feedService.train();
        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    // @RequiresPermissions("knownact:follow:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        followService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    // @RequiresPermissions("knownact:follow:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<FollowDTO> list = followService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, FollowExcel.class);
    }

}