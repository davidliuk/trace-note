package cn.neud.knownact.post.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.neud.knownact.client.feed.FeedFeignClient;
import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.common.exception.ErrorCode;
import cn.neud.knownact.model.dto.feed.FeedDTO;
import cn.neud.knownact.model.dto.page.PageData;
import cn.neud.knownact.model.dto.post.*;
import cn.neud.knownact.model.dto.user.UserDTO;
import cn.neud.knownact.model.vo.PostVO;
import cn.neud.knownact.post.service.PostService;
import cn.neud.knownact.client.user.UserFeignClient;
import cn.neud.knownact.post.service.RateService;
import cn.neud.knownact.post.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.neud.knownact.model.dto.page.DeleteRequest;
import cn.neud.knownact.common.utils.ResultUtils;
import cn.neud.knownact.model.constant.Constant;
import cn.neud.knownact.common.exception.BusinessException;
import cn.neud.knownact.model.entity.post.PostEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private TagService tagService;

    @Resource
    private RateService rateService;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private FeedFeignClient feedFeignClient;

    // region 增删改查

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    // @RequiresPermissions("knownact:belong:page")
    public Result<PageData<PostVO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        try {
            int size = (int) params.get(Constant.LIMIT) / 2;
            params.put(Constant.LIMIT, size);
            List<Long> posts = postService.page(params).getList().stream().map(PostDTO::getId).collect(Collectors.toList());
            List<Long> feeds = feedFeignClient.page(params).getData().getList().stream().map(FeedDTO::getPostId).collect(Collectors.toList());
            posts.addAll(feeds);
            List<PostVO> list = posts.stream().map(id -> getPostById(id).getData()).collect(Collectors.toList());
            PageData<PostVO> page = new PageData<>(list, size * 2L);
            return new Result<PageData<PostVO>>().ok(page);
        } catch (Exception e) {
            PageData<PostDTO> posts = postService.page(params);
            List<PostVO> list = posts.getList().stream().map(post -> getPostById(post.getId()).getData()).collect(Collectors.toList());
            PageData<PostVO> page = new PageData<>(list, (int) params.get(Constant.LIMIT));
            return new Result<PageData<PostVO>>().ok(page);
        }
    }

    /**
     * 创建
     *
     * @param postAddRequest
     * @param request
     * @return
     */
    @SaCheckLogin// @AuthCheck
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
    @SaCheckLogin// @AuthCheck
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
    @SaCheckLogin // @AuthCheck
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
    public Result<PostVO> getPostById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        PostEntity post = postService.selectById(id);
        PostVO postVO = new PostVO();
        BeanUtils.copyProperties(post, postVO);
        postVO.setUserVO(userFeignClient.getUserById(post.getUserId()).getData());
        postVO.setTags(tagService.getTags(post.getId()));
        if (StpUtil.isLogin()) {
            long userId = StpUtil.getLoginIdAsLong();
            postVO.setRate(rateService.get(userId, id));
        }
        return ResultUtils.success(postVO);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param postQueryRequest
     * @return
     */
    @SaCheckLogin// @AuthCheck(mustRole = "admin")
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
