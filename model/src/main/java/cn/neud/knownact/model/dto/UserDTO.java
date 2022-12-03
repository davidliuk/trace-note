package cn.neud.knownact.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
	private String icon;

	@ApiModelProperty(value = "")
	private String role;

	@ApiModelProperty(value = "")
	private Date createTime;

	@ApiModelProperty(value = "")
	private Date updateTime;

	@ApiModelProperty(value = "")
	private Integer isDeleted;


}