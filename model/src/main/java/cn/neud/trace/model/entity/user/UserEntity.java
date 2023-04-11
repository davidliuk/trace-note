package cn.neud.trace.model.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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