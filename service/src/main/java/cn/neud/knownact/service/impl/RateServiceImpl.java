package cn.neud.knownact.service.impl;

import cn.neud.knownact.dao.RateDao;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.knownact.common.service.impl.CrudServiceImpl;
import cn.neud.knownact.model.dto.RateDTO;
import cn.neud.knownact.model.entity.RateEntity;
import cn.neud.knownact.service.RateService;
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
public class RateServiceImpl extends CrudServiceImpl<RateDao, RateEntity, RateDTO> implements RateService {

    @Override
    public QueryWrapper<RateEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<RateEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}