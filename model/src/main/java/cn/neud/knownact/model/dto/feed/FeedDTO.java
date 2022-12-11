package cn.neud.knownact.model.dto.feed;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
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
public class FeedDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	private Long id;

	@ApiModelProperty(value = "")
	private Long userId;

	@ApiModelProperty(value = "")
	private Long postId;

//	@ApiModelProperty(value = "")
//	private Long tagId;

	@ApiModelProperty(value = "")
	private Double rate;


}