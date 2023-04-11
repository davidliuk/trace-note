package cn.neud.trace.model.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Data
@TableName("follow")
public class FollowEntity implements Serializable {

    /**
     * 关注人
     */
	private Long follower;
    /**
     * 被关注人
     */
	private Long followee;
    /**
     * 信任程度，0-5，默认1
     */
	private Integer rate;
}