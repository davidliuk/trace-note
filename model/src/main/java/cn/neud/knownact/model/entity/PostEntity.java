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
@TableName("post")
public class PostEntity implements Serializable {

    /**
     * 
     */
	private Long id;
    /**
     * 
     */
	private Long userId;
    /**
     * 
     */
	private String title;
    /**
     * 
     */
	private String content;
    /**
     * 
     */
	private Date createTime;
    /**
     * 
     */
	private Date updateTime;
    /**
     * 
     */
	private Long like;
    /**
     * 
     */
	private Long dislike;
    /**
     * 
     */
	private Long favorite;
    /**
     * 
     */
	private Integer reviewStatus;
    /**
     * 
     */
	private Integer isDeleted;
}