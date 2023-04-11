package cn.neud.trace.model.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


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
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long follower;

	@ApiModelProperty(value = "被关注人")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long followee;

	@ApiModelProperty(value = "信任程度，0-5，默认1")
	private Integer rate;


}