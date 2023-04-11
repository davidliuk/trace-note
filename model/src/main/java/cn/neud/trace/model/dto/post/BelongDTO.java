package cn.neud.trace.model.dto.post;

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
public class BelongDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	private Long postId;

	@ApiModelProperty(value = "")
	private Long tagId;


}