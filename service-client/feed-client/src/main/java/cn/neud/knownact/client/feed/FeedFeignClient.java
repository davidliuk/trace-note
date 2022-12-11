package cn.neud.knownact.client.feed;

import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.model.constant.Constant;
import cn.neud.knownact.model.dto.feed.FeedDTO;
import cn.neud.knownact.model.dto.page.PageData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@FeignClient(value = "knownact-feed", name = "knownact-feed" ,url = "localhost:8082", path = "/api/feed")
@Repository
public interface FeedFeignClient {

    @GetMapping("/feed")
    @ApiOperation("答者统计数据")
    public Result<String> train();

    @GetMapping("/feed/page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    // @RequiresPermissions("knownact:feed:page")
    public Result<PageData<FeedDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params);
}
