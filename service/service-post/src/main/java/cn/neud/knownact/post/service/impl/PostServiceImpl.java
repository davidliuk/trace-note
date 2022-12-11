package cn.neud.knownact.post.service.impl;

import cn.neud.knownact.common.exception.ErrorCode;
import cn.neud.knownact.common.service.impl.CrudServiceImpl;
import cn.neud.knownact.post.dao.PostDao;
import cn.neud.knownact.model.dto.post.PostDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.knownact.common.exception.BusinessException;
import cn.neud.knownact.model.entity.post.PostEntity;
import cn.neud.knownact.model.enums.PostReviewStatusEnum;
import cn.neud.knownact.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author davidli
 * @description 针对表【post(帖子)】的数据库操作Service实现
 */
@Service
@Slf4j
public class PostServiceImpl extends CrudServiceImpl<PostDao, PostEntity, PostDTO> implements PostService {

    @Override
    public QueryWrapper<PostEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<PostEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public void validPost(PostEntity post, boolean add) {
        if (post == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String content = post.getContent();
        String title = post.getTitle();
        Integer reviewStatus = post.getReviewStatus();
        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(content, title)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(title) && content.length() > 200) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题过长");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
        if (reviewStatus != null && !PostReviewStatusEnum.getValues().contains(reviewStatus)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    @Override
    public List<PostEntity> list(QueryWrapper<PostEntity> queryWrapper) {
        return baseDao.selectList(queryWrapper);
    }

    @Override
    public long like(Long postId, boolean asc) {
        PostEntity post = baseDao.selectById(postId);
        post.like(asc);
        baseDao.updateById(post);
        return post.getLikes();
    }

    @Override
    public long dislike(Long postId, boolean asc) {
        PostEntity post = baseDao.selectById(postId);
        post.dislike(asc);
        baseDao.updateById(post);
        return post.getDislike();
    }

    @Override
    public long favorite(Long postId, boolean asc) {
        PostEntity post = baseDao.selectById(postId);
        post.favorite(asc);
        baseDao.updateById(post);
        return post.getFavorite();
    }
}
