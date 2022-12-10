package cn.neud.knownact.user.client;

import cn.neud.knownact.common.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "knownact-feed", name = "knownact-feed" ,url = "localhost:8082")
@Repository
public interface FeedFeignClient {

    @GetMapping("/api/feed/feed")
    @ApiOperation("答者统计数据")
    public Result<String> train();

}
