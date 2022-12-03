package cn.neud.knownact.service;

import cn.neud.knownact.common.service.CrudService;
import cn.neud.knownact.model.dto.PostDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.neud.knownact.model.entity.PostEntity;

import java.util.List;

/**
 * @author davidli
 * @description 针对表【post(帖子)】的数据库操作Service
 */
public interface PostService extends CrudService<PostEntity, PostDTO> {

    /**
     * 校验
     *
     * @param post
     * @param add 是否为创建校验
     */
    void validPost(PostEntity post, boolean add);


    List<PostEntity> list(QueryWrapper<PostEntity> queryWrapper);
}
