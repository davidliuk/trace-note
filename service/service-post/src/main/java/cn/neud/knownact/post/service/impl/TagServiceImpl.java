package cn.neud.knownact.post.service.impl;

import cn.neud.knownact.post.dao.TagDao;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.knownact.common.service.impl.CrudServiceImpl;
import cn.neud.knownact.model.dto.post.TagDTO;
import cn.neud.knownact.model.entity.post.TagEntity;
import cn.neud.knownact.post.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-02
 */
@Service
@Slf4j
public class TagServiceImpl extends CrudServiceImpl<TagDao, TagEntity, TagDTO> implements TagService {

    @Override
    public QueryWrapper<TagEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TagEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}