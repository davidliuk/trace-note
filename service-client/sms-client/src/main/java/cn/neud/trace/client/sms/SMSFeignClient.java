package cn.neud.trace.client.sms;

import cn.neud.trace.common.annotation.LogOperation;
import cn.neud.trace.common.utils.Result;
import cn.neud.trace.model.dto.user.UserEmailDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "trace-sms", name = "trace-sms", url = "localhost:8085", path = "/api/sms")
@Repository
public interface SMSFeignClient {

    @GetMapping("/mail/code")
    @ApiOperation("邮箱获取验证码")
    @LogOperation("邮箱获取验证码")
    public Result loginByEmail(@RequestBody UserEmailDTO userEmailDTO);

}
