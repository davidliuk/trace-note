package cn.neud.knownact.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.neud.knownact.common.utils.ConvertUtils;
import cn.neud.knownact.model.entity.post.RateEntity;
import cn.neud.knownact.post.dao.RateDao;
import cn.neud.knownact.post.service.PostService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.knownact.common.service.impl.CrudServiceImpl;
import cn.neud.knownact.model.dto.post.RateDTO;
import cn.neud.knownact.post.service.RateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public RateDTO get(Long userId, Long postId) {
        LambdaQueryWrapper<RateEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RateEntity::getUserId, userId).eq(RateEntity::getPostId, postId);

        return ConvertUtils.sourceToTarget(baseDao.selectOne(wrapper), currentDtoClass());
    }

    @Override
    public List<Long> selectLikeByUser(Long userId) {
        LambdaQueryWrapper<RateEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RateEntity::getUserId, userId).eq(RateEntity::getLikes, 1);
        return baseDao.selectList(wrapper).stream().map(RateEntity::getPostId).collect(Collectors.toList());
    }

    @Override
    public List<Long> selectDislikeByUser(Long userId) {
        LambdaQueryWrapper<RateEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RateEntity::getUserId, userId).eq(RateEntity::getDislike, 1);
        return baseDao.selectList(wrapper).stream().map(RateEntity::getPostId).collect(Collectors.toList());
    }

    @Override
    public List<Long> selectFavoriteByUser(Long userId) {
        LambdaQueryWrapper<RateEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RateEntity::getUserId, userId).eq(RateEntity::getFavorite, 1);
        return baseDao.selectList(wrapper).stream().map(RateEntity::getPostId).collect(Collectors.toList());
    }
}