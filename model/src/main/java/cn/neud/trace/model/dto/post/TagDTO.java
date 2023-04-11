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
@ApiModel(value = "Tag")
public class TagDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String icon;

	private Long postArticleCount;
	private Long concern_user_count;


}