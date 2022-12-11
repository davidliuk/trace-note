package cn.neud.knownact.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.model.dto.UserDTO;
import cn.neud.knownact.model.entity.RateEntity;
import cn.neud.knownact.model.entity.UserEntity;
import cn.neud.knownact.post.dao.RateDao;
import cn.neud.knownact.post.service.PostService;
import cn.neud.knownact.user.client.UserFeignClient;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.knownact.common.service.impl.CrudServiceImpl;
import cn.neud.knownact.model.dto.RateDTO;
import cn.neud.knownact.post.service.RateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static cn.neud.knownact.model.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-02
 */
@Service
@Slf4j
public class RateServiceImpl extends CrudServiceImpl<RateDao, RateEntity, RateDTO> implements RateService {

    @Resource
    PostService postService;

//    @Resource
//    private UserFeignClient userFeignClient;

    @Override
    public QueryWrapper<RateEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        QueryWrapper<RateEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Transactional
    @Override
    public long like(Long postId) {
//        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        Long userId = ((UserEntity) request.getSession().getAttribute(USER_LOGIN_STATE)).getId();
//        UserDTO user = userFeignClient.getLoginUser().getData();
//        Long userId = user.getId();
        Long userId = StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<RateEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RateEntity::getUserId, userId).eq(RateEntity::getPostId, postId);
        if (baseDao.selectList(wrapper).size() != 0) {
            RateEntity rate = baseDao.selectOne(wrapper);
            boolean flag = rate.switchLike();
            rate.updateRate();
            baseDao.update(rate, wrapper);
            return postService.like(postId, flag);
        }
        RateEntity entity = new RateEntity();
        entity.setUserId(userId);
        entity.setPostId(postId);
        entity.setLikes(1);
        entity.updateRate();
        baseDao.insert(entity);
        return postService.like(postId, true);
    }

    @Transactional
    @Override
    public long dislike(Long postId) {
//        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        Long userId = ((UserEntity) request.getSession().getAttribute(USER_LOGIN_STATE)).getId();
//        UserDTO user = userFeignClient.getLoginUser().getData();
//        Long userId = user.getId();
        Long userId = StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<RateEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RateEntity::getUserId, userId).eq(RateEntity::getPostId, postId);
        if (baseDao.selectList(wrapper).size() != 0) {
            RateEntity rate = baseDao.selectOne(wrapper);
            boolean flag = rate.switchDislike();
            rate.updateRate();
            baseDao.update(rate, wrapper);
            return postService.dislike(postId, flag);
        }
        RateEntity entity = new RateEntity();
        entity.setUserId(userId);
        entity.setPostId(postId);
        entity.setDislike(1);
        entity.updateRate();
        baseDao.insert(entity);
        return postService.dislike(postId, true);
    }

    @Transactional
    @Override
    public long favorite(Long postId) {
//        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        Long userId = ((UserEntity) request.getSession().getAttribute(USER_LOGIN_STATE)).getId();
//        UserDTO user = userFeignClient.getLoginUser().getData();
//        Long userId = user.getId();
        Long userId = StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<RateEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RateEntity::getUserId, userId).eq(RateEntity::getPostId, postId);
        if (baseDao.selectList(wrapper).size() != 0) {
            RateEntity rate = baseDao.selectOne(wrapper);
            boolean flag = rate.switchFavorite();
            rate.updateRate();
            baseDao.update(rate, wrapper);
            return postService.favorite(postId, flag);
        }
        RateEntity entity = new RateEntity();
        entity.setUserId(userId);
        entity.setPostId(postId);
        entity.setFavorite(1);
        entity.updateRate();
        baseDao.insert(entity);
        return postService.favorite(postId, true);
    }
}