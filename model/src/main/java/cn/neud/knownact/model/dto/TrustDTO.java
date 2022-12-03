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
public class TrustDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	private Integer userA;

	@ApiModelProperty(value = "")
	private Integer userB;

	@ApiModelProperty(value = "")
	private Integer rate;

	@ApiModelProperty(value = "")
	private Integer like;

	@ApiModelProperty(value = "")
	private Integer dislike;

	@ApiModelProperty(value = "")
	private Integer favorite;


}