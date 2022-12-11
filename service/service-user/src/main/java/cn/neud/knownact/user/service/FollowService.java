package cn.neud.knownact.user.service;

import cn.neud.knownact.common.service.CrudService;
import cn.neud.knownact.model.dto.user.FollowDTO;
import cn.neud.knownact.model.entity.user.FollowEntity;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
public interface FollowService extends CrudService<FollowEntity, FollowDTO> {

    boolean set(Long id);
}