package cn.neud.knownact.client.user;

import cn.neud.knownact.common.annotation.LogOperation;
import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.model.dto.user.UserDTO;
import cn.neud.knownact.model.vo.UserVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value = "knownact-user2", name = "knownact-user2", url = "localhost:8081", path = "/api/user")
@Repository
public interface UserFeignClient {

    @GetMapping("/user/get/login")
    @ApiOperation("获取当前用户")
    @LogOperation("获取当前用户")
    public Result<UserDTO> getLoginUser(HttpServletRequest request);

    @GetMapping("/user/get/{id}")
    @ApiOperation("根据ID获取获取用户视图")
    @LogOperation("根据ID获取获取用户视图")
    public Result<UserVO> getUserById(@PathVariable("id") Long id);
}
