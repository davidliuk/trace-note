package cn.neud.knownact.model.vo;

import cn.neud.knownact.model.entity.post.PostEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 帖子视图
 *
 * @author david
 * @TableName product
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostVO extends PostEntity {

    /**
     * 是否已点赞
     */
    private Boolean hasThumb;

    private static final long serialVersionUID = 1L;
}