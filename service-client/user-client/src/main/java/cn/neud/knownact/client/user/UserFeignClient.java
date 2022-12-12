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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@FeignClient(value = "knownact-user", name = "knownact-user", url = "localhost:8081", path = "/api/user")
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

    /**
     * 根据 id 批量获取用户
     *
     * @param ids
     * @return
     */
    @PostMapping("/user/get")
    @ApiOperation("根据ID获取获取用户视图")
    @LogOperation("根据ID获取获取用户视图")
    public Result<Map<Long, UserVO>> getUserByIdBatch(@RequestBody Long[] ids);
}
