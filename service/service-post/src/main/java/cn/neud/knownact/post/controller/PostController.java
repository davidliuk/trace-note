package cn.neud.knownact.post.controller;

import cn.neud.knownact.common.annotation.AuthCheck;
import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.common.exception.ErrorCode;
import cn.neud.knownact.model.dto.UserDTO;
import cn.neud.knownact.model.dto.post.PostAddRequest;
import cn.neud.knownact.model.dto.post.PostQueryRequest;
import cn.neud.knownact.model.dto.post.PostUpdateRequest;
import cn.neud.knownact.model.entity.UserEntity;
import cn.neud.knownact.post.service.PostService;
import cn.neud.knownact.user.client.UserFeignClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.neud.knownact.model.dto.page.DeleteRequest;
import cn.neud.knownact.common.utils.ResultUtils;
import cn.neud.knownact.model.constant.Constant;
import cn.neud.knownact.common.exception.BusinessException;
import cn.neud.knownact.model.entity.PostEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author david
 */
@RestController
@RequestMapping("/post")
@Slf4j
public class PostController {

    @Resource
    private PostService postService;

    @Resource
    private UserFeignClient userFeignClient;

    // region 增删改查

    /**
     * 创建
     *
     * @param postAddRequest
     * @param request
     * @return
     */
    @AuthCheck
    @PostMapping("/add")
    public Result<Long> addPost(@RequestBody PostAddRequest postAddRequest, HttpServletRequest request) {
        if (postAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        PostEntity post = new PostEntity();
        BeanUtils.copyProperties(postAddRequest, post);
        // 校验
        postService.validPost(post, true);
        UserDTO loginUser = userFeignClient.getLoginUser(request).getData();
        post.setUserId(loginUser.getId());
        boolean result = postService.insert(post);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newPostId = post.getId();
        return ResultUtils.success(newPostId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @AuthCheck
    @PostMapping("/delete")
    public Result<Boolean> deletePost(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserDTO user = userFeignClient.getLoginUser(request).getData();
        long id = deleteRequest.getId();
        // 判断是否存在
        PostEntity oldPost = postService.selectById(id);
        if (oldPost == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldPost.getUserId().equals(user.getId()) && !user.isAdmin()) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = postService.deleteById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param postUpdateRequest
     * @param request
     * @return
     */
    @AuthCheck
    @PostMapping("/update")
    public Result<Boolean> updatePost(@RequestBody PostUpdateRequest postUpdateRequest,
                                      HttpServletRequest request) {
        if (postUpdateRequest == null || postUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        PostEntity post = new PostEntity();
        BeanUtils.copyProperties(postUpdateRequest, post);
        // 参数校验
        postService.validPost(post, false);
        UserDTO user = userFeignClient.getLoginUser(request).getData();
        long id = postUpdateRequest.getId();
        // 判断是否存在
        PostEntity oldPost = postService.selectById(id);
        if (oldPost == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldPost.getUserId().equals(user.getId()) && !user.isAdmin()) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = postService.updateById(post);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public Result<PostEntity> getPostById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        PostEntity post = postService.selectById(id);
        return ResultUtils.success(post);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param postQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public Result<List<PostEntity>> listPost(PostQueryRequest postQueryRequest) {
        PostEntity postQuery = new PostEntity();
        if (postQueryRequest != null) {
            BeanUtils.copyProperties(postQueryRequest, postQuery);
        }
        QueryWrapper<PostEntity> queryWrapper = new QueryWrapper<>(postQuery);
        List<PostEntity> postList = postService.list(queryWrapper);
        return ResultUtils.success(postList);
    }

    /**
     * 分页获取列表
     *
     * @param postQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public Result<Page<PostEntity>> listPostByPage(PostQueryRequest postQueryRequest, HttpServletRequest request) {
        if (postQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        PostEntity postQuery = new PostEntity();
        BeanUtils.copyProperties(postQueryRequest, postQuery);
        long current = postQueryRequest.getCurrent();
        long size = postQueryRequest.getPageSize();
        String sortField = postQueryRequest.getSortField();
        String sortOrder = postQueryRequest.getSortOrder();
        String content = postQuery.getContent();
        // content 需支持模糊搜索
        postQuery.setContent(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<PostEntity> queryWrapper = new QueryWrapper<>(postQuery);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(Constant.ASC), sortField);
        Page<PostEntity> postPage = postService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(postPage);
    }

    // endregion

}
