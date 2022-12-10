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
@TableName("user")
public class UserEntity implements Serializable {

    /**
     * 
     */
	private Long id;
    /**
     * 
     */
	private String account;
    /**
     * 
     */
	private String password;
    /**
     * 
     */
	private String phone;
    /**
     * 
     */
	private String nickname;
    /**
     * 
     */
	private String avatar;
    /**
     * 
     */
	private String role;
    /**
     *
     */
    private Long followeeCount;
    /**
     *
     */
    private Long followerCount;
    /**
     *
     */
    private Long postArticleCount;
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
	private Integer isDeleted;
}