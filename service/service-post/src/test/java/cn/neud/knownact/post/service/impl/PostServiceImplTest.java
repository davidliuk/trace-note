package cn.neud.knownact.post.service.impl;

import cn.neud.knownact.common.utils.DateUtils;
import cn.neud.knownact.model.entity.post.PostEntity;
import cn.neud.knownact.post.dao.PostDao;
import cn.neud.knownact.post.service.PostService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SpringBootTest
class PostServiceImplTest {

    @Resource
    private PostService postService;
    @Resource
    private PostDao postDao;

    @Test
    public void fillTableRate() {
        LambdaQueryWrapper<PostEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.isNull(PostEntity::getLikes).or().isNull(PostEntity::getDislike).or().isNull(PostEntity::getFavorite);
        List<PostEntity> posts = postDao.selectList(wrapper).stream().peek(postEntity -> {
            if (postEntity.getLikes() == null || postEntity.getLikes() == 0) {
                postEntity.setLikes((long) new Random().nextInt(500));
            }
            if (postEntity.getDislike() == null || postEntity.getDislike() == 0) {
                postEntity.setDislike((long) new Random().nextInt(500));
            }
            if (postEntity.getFavorite() == null || postEntity.getFavorite() == 0) {
                postEntity.setFavorite((long) new Random().nextInt(500));
            }
        }).collect(Collectors.toList());
        System.out.println(posts);
        for (PostEntity post : posts) {
            System.out.println(post);
            postDao.updateById(post);
        }
    }

    @Test
    public void fillTableDate() {
        LambdaQueryWrapper<PostEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.isNull(PostEntity::getCreateTime).or().isNull(PostEntity::getUpdateTime);
        List<PostEntity> posts = postDao.selectList(wrapper).stream().peek(postEntity -> {
            if (postEntity.getCreateTime() == null) {
                postEntity.setCreateTime(DateUtils.random("2021-01-01"));
            }
            if (postEntity.getUpdateTime() == null) {
                postEntity.setUpdateTime(DateUtils.random(postEntity.getCreateTime()));
            }
        }).collect(Collectors.toList());
        System.out.println(posts);
        for (PostEntity post : posts) {
            System.out.println(post);
            postDao.updateById(post);
        }
    }

}
