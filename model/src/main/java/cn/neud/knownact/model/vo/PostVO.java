package cn.neud.knownact.model.vo;

import cn.neud.knownact.model.dto.post.RateDTO;
import cn.neud.knownact.model.entity.post.PostEntity;
import cn.neud.knownact.model.entity.post.TagEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 帖子视图
 *
 * @author david
 * @TableName post
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostVO extends PostEntity {
    /**
     * 是否已点赞
     */
    private Boolean hasLike = false;
    /**
     * 是否已点踩
     */
    private Boolean hasDislike = false;
    /**
     * 是否已收藏
     */
    private Boolean hasFavorite = false;
    /**
     * 发布者信息
     */
    private UserVO userVO;
    /**
     * 文章标签
     */
    private List<TagEntity> tags;

    private static final long serialVersionUID = 1L;

    public void setRate(RateDTO rate) {
        if (rate == null) {
            return;
        }
        hasLike = rate.getLikes() == 1;
        hasDislike = rate.getDislike() == 1;
        hasFavorite = rate.getFavorite() == 1;
    }
}