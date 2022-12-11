package cn.neud.knownact.user.service.impl;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.knownact.common.service.impl.CrudServiceImpl;
import cn.neud.knownact.user.dao.FollowDao;
import cn.neud.knownact.model.dto.user.FollowDTO;
import cn.neud.knownact.model.entity.user.FollowEntity;
import cn.neud.knownact.user.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
@Service
@Slf4j
public class FollowServiceImpl extends CrudServiceImpl<FollowDao, FollowEntity, FollowDTO> implements FollowService {

    @Override
    public QueryWrapper<FollowEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<FollowEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


    @Override
    public boolean set(Long followee) {
//        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        Long follower = ((UserEntity) request.getSession().getAttribute(USER_LOGIN_STATE)).getId();
        Long follower = StpUtil.getLoginIdAsLong();
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

    @SaCheckLogin
    @Override
    public boolean isFollow(Long followee) {
        Long follower = StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<FollowEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FollowEntity::getFollower, follower).eq(FollowEntity::getFollowee, followee);
        return baseDao.selectList(wrapper).size() == 1;
    }
}