package cn.neud.knownact.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import static cn.neud.knownact.model.constant.UserConstant.ADMIN_ROLE;


/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Data
@ApiModel(value = "")
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	private Long id;

	@ApiModelProperty(value = "")
	private String account;

	@ApiModelProperty(value = "")
	private String password;

	@ApiModelProperty(value = "")
	private String phone;

	@ApiModelProperty(value = "")
	private String nickname;

	@ApiModelProperty(value = "")
	private String avatar;

	@ApiModelProperty(value = "")
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

	@ApiModelProperty(value = "")
	private Date createTime;

	@ApiModelProperty(value = "")
	private Date updateTime;

	@ApiModelProperty(value = "")
	private Integer isDeleted;

	public boolean isAdmin() {
		return ADMIN_ROLE.equals(this.getRole());
	}
}