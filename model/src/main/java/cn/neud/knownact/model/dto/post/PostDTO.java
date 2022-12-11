package cn.neud.knownact.model.dto.post;

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
public class PostDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	private Long id;

	@ApiModelProperty(value = "")
	private Long userId;

	@ApiModelProperty(value = "")
	private String title;

	@ApiModelProperty(value = "")
	private String content;

	@ApiModelProperty(value = "")
	private String brief;

	@ApiModelProperty(value = "封面图片")
	private String coverImage;

	@ApiModelProperty(value = "")
	private Date createTime;

	@ApiModelProperty(value = "")
	private Date updateTime;

	@ApiModelProperty(value = "")
	private Long likes;

	@ApiModelProperty(value = "")
	private Long dislike;

	@ApiModelProperty(value = "")
	private Long favorite;

	@ApiModelProperty(value = "")
	private Integer reviewStatus;

	@ApiModelProperty(value = "")
	private Integer isDeleted;

	@ApiModelProperty(value = "当前用户的评分状态")
	private RateDTO rate;

}