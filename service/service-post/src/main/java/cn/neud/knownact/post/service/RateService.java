package cn.neud.knownact.post.service;

import cn.neud.knownact.common.service.CrudService;
import cn.neud.knownact.model.dto.post.RateDTO;
import cn.neud.knownact.model.entity.post.RateEntity;

import java.util.List;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-02
 */
public interface RateService extends CrudService<RateEntity, RateDTO> {

    long like(Long postId);
    long dislike(Long postId);
    long favorite(Long postId);
    RateDTO get(Long userId, Long postId);
    List<Long> selectLikeByUser(Long userId);
    List<Long> selectDislikeByUser(Long userId);
    List<Long> selectFavoriteByUser(Long userId);
}