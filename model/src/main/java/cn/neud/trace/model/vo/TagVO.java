package cn.neud.trace.model.vo;

import cn.neud.trace.model.entity.post.TagEntity;
import lombok.Data;

/**
 * 标签视图
 *
 * @author david
 * @TableName tag
 */
@Data
public class TagVO extends TagEntity {
    /**
     * 是否已关注
     */
    private Boolean hasConcern = false;

}
