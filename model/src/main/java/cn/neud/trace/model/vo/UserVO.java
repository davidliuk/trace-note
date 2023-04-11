package cn.neud.trace.model.vo;

import cn.neud.trace.model.entity.user.UserEntity;
import lombok.Data;

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