package cn.neud.knownact.user.service;

import cn.neud.knownact.common.service.CrudService;
import cn.neud.knownact.model.dto.RateDTO;
import cn.neud.knownact.model.entity.RateEntity;

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
}