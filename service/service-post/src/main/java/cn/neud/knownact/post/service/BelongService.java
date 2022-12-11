package cn.neud.knownact.post.service;

import cn.neud.knownact.common.service.CrudService;
import cn.neud.knownact.model.dto.post.BelongDTO;
import cn.neud.knownact.model.entity.post.BelongEntity;
import java.util.List;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-02
 */
public interface BelongService extends CrudService<BelongEntity, BelongDTO> {

    List<Long> getTagsId(Long postId);
}