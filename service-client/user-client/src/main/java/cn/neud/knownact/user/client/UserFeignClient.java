package cn.neud.knownact.user.client;

import cn.neud.knownact.common.annotation.LogOperation;
import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.model.dto.UserDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@FeignClient(value = "knownact-user", name = "knownact-user", url = "localhost:8081", path = "/api/user")
@Repository
public interface UserFeignClient {

    @GetMapping("/user/get/login")
    @ApiOperation("获取当前用户")
    @LogOperation("获取当前用户")
    public Result<UserDTO> getLoginUser(HttpServletRequest request);
    
}
