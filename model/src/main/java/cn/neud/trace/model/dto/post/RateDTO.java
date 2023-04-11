package cn.neud.trace.model.dto.post;

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
public class RateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long userId;

	@ApiModelProperty(value = "帖子id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long postId;

	@ApiModelProperty(value = "评分0-5")
	private Integer rate;

	@ApiModelProperty(value = "")
	private Integer likes;

	@ApiModelProperty(value = "")
	private Integer dislike;

	@ApiModelProperty(value = "")
	private Integer favorite;


}