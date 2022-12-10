package cn.neud.knownact.user.client;

import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.model.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@FeignClient(value = "knownact-user", name = "knownact-user", url = "localhost:8081")
//@RequestMapping("/user")
@Repository
public interface UserFeignClient {

    @GetMapping("/api/user/user/get/login")
    public Result<UserDTO> getLoginUser(HttpServletRequest request);
    
}
