package cn.neud.knownact.model.dto.user;

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
public class FollowDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "关注人")
	private Long follower;

	@ApiModelProperty(value = "被关注人")
	private Long followee;

	@ApiModelProperty(value = "信任程度，0-5，默认1")
	private Integer rate;


}