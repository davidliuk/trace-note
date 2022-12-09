package cn.neud.knownact.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.knownact.common.service.impl.CrudServiceImpl;
import cn.neud.knownact.user.dao.FollowDao;
import cn.neud.knownact.model.dto.FollowDTO;
import cn.neud.knownact.model.entity.FollowEntity;
import cn.neud.knownact.user.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Service
@Slf4j
public class FollowServiceImpl extends CrudServiceImpl<FollowDao, FollowEntity, FollowDTO> implements FollowService {

    @Override
    public QueryWrapper<FollowEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<FollowEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}