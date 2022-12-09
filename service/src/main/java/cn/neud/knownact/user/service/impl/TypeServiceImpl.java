package cn.neud.knownact.user.service.impl;

import cn.neud.knownact.user.dao.TypeDao;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.knownact.common.service.impl.CrudServiceImpl;
import cn.neud.knownact.model.dto.TypeDTO;
import cn.neud.knownact.model.entity.TypeEntity;
import cn.neud.knownact.user.service.TypeService;
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
public class TypeServiceImpl extends CrudServiceImpl<TypeDao, TypeEntity, TypeDTO> implements TypeService {

    @Override
    public QueryWrapper<TypeEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TypeEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}