package cn.neud.knownact.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Data
@TableName("rate")
public class RateEntity implements Serializable {

    /**
     * 用户id
     */
	private Long follower;
    /**
     * 帖子id
     */
	private Long followee;
    /**
     * 评分0-5
     */
	private Integer rate;
    /**
     * 
     */
	private Integer like;
    /**
     * 
     */
	private Integer dislike;
    /**
     * 
     */
	private Integer favorite;
}