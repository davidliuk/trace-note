package cn.neud.knownact.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Data
@TableName("user")
public class UserEntity {

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
	private String icon;
    /**
     * 
     */
	private String role;
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