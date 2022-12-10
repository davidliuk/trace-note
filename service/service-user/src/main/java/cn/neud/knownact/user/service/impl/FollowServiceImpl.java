package cn.neud.knownact.user.service.impl;

import cn.neud.knownact.model.entity.UserEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.knownact.common.service.impl.CrudServiceImpl;
import cn.neud.knownact.user.dao.FollowDao;
import cn.neud.knownact.model.dto.FollowDTO;
import cn.neud.knownact.model.entity.FollowEntity;
import cn.neud.knownact.user.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static cn.neud.knownact.model.constant.UserConstant.USER_LOGIN_STATE;

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


    @Override
    public boolean set(Long followee) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Long follower = ((UserEntity) request.getSession().getAttribute(USER_LOGIN_STATE)).getId();
        LambdaQueryWrapper<FollowEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FollowEntity::getFollower, follower).eq(FollowEntity::getFollowee, followee);
        if (baseDao.selectList(wrapper).size() != 0) {
            baseDao.delete(wrapper);
            return false;
        }
        FollowEntity entity = new FollowEntity();
        entity.setFollower(follower);
        entity.setFollowee(followee);
        entity.setRate(1);
        baseDao.insert(entity);
        return true;
    }
}