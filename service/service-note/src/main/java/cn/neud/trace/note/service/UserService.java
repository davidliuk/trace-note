package cn.neud.trace.note.service;

import cn.neud.trace.note.model.dto.UserDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.neud.trace.note.model.dto.LoginFormDTO;
import cn.neud.trace.note.model.dto.Result;
import cn.neud.trace.note.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author David
 * @since 2021-12-22
 */
public interface UserService extends IService<User> {

    Result sendCode(String phone, HttpSession session);

    Result login(LoginFormDTO loginForm, HttpSession session);

    Result register(String userAccount, String password, String checkPassword);

    Result sign();

    Result signCount();

    UserDTO getLoginUser(HttpServletRequest request);
}
