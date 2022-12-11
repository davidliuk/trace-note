package cn.neud.knownact.sms.client;

import cn.neud.knownact.common.annotation.LogOperation;
import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.model.dto.user.UserEmailDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "knownact-sms", name = "knownact-sms", url = "localhost:8085", path = "/api/sms")
@Repository
public interface SMSFeignClient {

    @GetMapping("/mail/code")
    @ApiOperation("邮箱获取验证码")
    @LogOperation("邮箱获取验证码")
    public Result loginByEmail(@RequestBody UserEmailDTO userEmailDTO);

}
