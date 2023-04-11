package cn.neud.trace.model.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新请求
 *
 * @author david
 */
@Data
public class UserUpdateRequest implements Serializable {
    /**
     * id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 用户角色: user, admin
     */
    private String userRole;

    /**
     * 密码
     */
    private String userPassword;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}