package cn.neud.knownact.model.vo;

import cn.neud.knownact.model.entity.user.UserEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户视图
 *
 * @author david
 * @TableName user
 */
@Data
public class UserVO extends UserEntity {
    /**
     * 是否已订阅
     */
    private Boolean hasFollow = false;

    private static final long serialVersionUID = 1L;
}