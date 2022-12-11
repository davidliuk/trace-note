package cn.neud.knownact.post.service;

import cn.neud.knownact.common.service.CrudService;
import cn.neud.knownact.model.dto.post.TagDTO;
import cn.neud.knownact.model.entity.post.TagEntity;

import java.util.List;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-02
 */
public interface TagService extends CrudService<TagEntity, TagDTO> {

    List<TagEntity> getTags(Long postId);

}