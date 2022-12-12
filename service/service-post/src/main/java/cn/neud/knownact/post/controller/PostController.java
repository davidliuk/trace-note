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
import cn.neud.knownact.model.vo.UserVO;
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
import org.apache.commons.collections4.map.HashedMap;
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
    @ApiOperation("推荐分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    // @RequiresPermissions("knownact:belong:page")
    public Result<PageData<PostVO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        Map<String, Object> origin = new HashedMap<>(params);
        try {
            // 尝试推荐一半内容是训练出的feed，一半内容是按时间排序出来的内容，来摆脱信息茧房
            int size = Integer.parseInt((String) params.get(Constant.LIMIT));
            params.put(Constant.LIMIT, String.valueOf(size / 2));
            Map<String, Object> two = new HashedMap<>(params);
            List<Long> feeds = feedFeignClient.page(two).getData().getList().stream().map(FeedDTO::getPostId).collect(Collectors.toList());
            System.out.println(feeds);
            params.put(Constant.LIMIT, String.valueOf(size - feeds.size()));
            List<PostDTO> dtos = postService.page(params).getList();
            List<Long> posts = dtos.stream().map(PostDTO::getId).collect(Collectors.toList());
            System.out.println(posts);
            posts.addAll(feeds);
            Map<Long, UserVO> userMap = userFeignClient.getUserByIdBatch(dtos.stream().map(PostDTO::getUserId).toArray(Long[]::new)).getData();
            System.out.println("userMap!!!!!");
            System.out.println(userMap);
            List<PostVO> list = posts.stream().map(id -> {
                PostEntity post = postService.selectById(id);
                PostVO postVO = new PostVO();
                BeanUtils.copyProperties(post, postVO);
                try {
                    postVO.setUserVO(userMap.getOrDefault(post.getUserId(), new UserVO()));
                    postVO.setTags(tagService.getTags(id));
                } catch (Exception e) {
                }
                if (StpUtil.isLogin()) {
                    long userId = StpUtil.getLoginIdAsLong();
                    postVO.setRate(rateService.get(userId, id));
                }
                return postVO;
            }).collect(Collectors.toList());
            System.out.println(list);
            PageData<PostVO> page = new PageData<>(list, list.size());
            return new Result<PageData<PostVO>>().ok(page);
        } catch (Exception e) {
            PageData<PostDTO> posts = postService.page(origin);
            Long[] ids = posts.getList().stream().map(PostDTO::getUserId).toArray(Long[]::new);
            System.out.println(ids);
            Map<Long, UserVO> userMap = userFeignClient.getUserByIdBatch(ids).getData();
            List<PostVO> list = posts.getList().stream().map(post -> {
                PostVO postVO = new PostVO();
                BeanUtils.copyProperties(post, postVO);
                try {
                    postVO.setUserVO(userMap.getOrDefault(post.getUserId(), new UserVO()));
                    postVO.setTags(tagService.getTags(post.getId()));
                } catch (Exception ex) {
                }
                if (StpUtil.isLogin()) {
                    long userId = StpUtil.getLoginIdAsLong();
                    postVO.setRate(rateService.get(userId, post.getId()));
                }
                return postVO;
            }).collect(Collectors.toList());
            PageData<PostVO> page = new PageData<>(list, list.size());
            return new Result<PageData<PostVO>>().ok(page);
        }
    }


    @SaCheckLogin
    @GetMapping("page/like")
    @ApiOperation("点赞列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    // @RequiresPermissions("knownact:belong:page")
    public Result<PageData<PostVO>> pageLike(@ApiIgnore @RequestParam Map<String, Object> params) {
        long userId = StpUtil.getLoginIdAsLong();
        params.put("ids", rateService.selectLikeByUser(userId));
        PageData<PostDTO> posts = postService.page(params);
        List<PostVO> list = posts.getList().stream().map(post -> getPostById(post.getId()).getData()).collect(Collectors.toList());
        PageData<PostVO> page = new PageData<>(list, list.size());
        return new Result<PageData<PostVO>>().ok(page);
    }

    @SaCheckLogin
    @GetMapping("page/dislike")
    @ApiOperation("点踩列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    // @RequiresPermissions("knownact:belong:page")
    public Result<PageData<PostVO>> pageDislike(@ApiIgnore @RequestParam Map<String, Object> params) {
        long userId = StpUtil.getLoginIdAsLong();
        params.put("ids", rateService.selectDislikeByUser(userId));
        PageData<PostDTO> posts = postService.page(params);
        List<PostVO> list = posts.getList().stream().map(post -> getPostById(post.getId()).getData()).collect(Collectors.toList());
        PageData<PostVO> page = new PageData<>(list, list.size());
        return new Result<PageData<PostVO>>().ok(page);
    }

    @SaCheckLogin
    @GetMapping("page/favorite")
    @ApiOperation("收藏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    // @RequiresPermissions("knownact:belong:page")
    public Result<PageData<PostVO>> pageFavorite(@ApiIgnore @RequestParam Map<String, Object> params) {
        long userId = StpUtil.getLoginIdAsLong();
        params.put("ids", rateService.selectFavoriteByUser(userId));
        PageData<PostDTO> posts = postService.page(params);
        List<PostVO> list = posts.getList().stream().map(post -> getPostById(post.getId()).getData()).collect(Collectors.toList());
        PageData<PostVO> page = new PageData<>(list, list.size());
        return new Result<PageData<PostVO>>().ok(page);
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
        try {
            postVO.setUserVO(userFeignClient.getUserById(post.getUserId()).getData());
            postVO.setTags(tagService.getTags(post.getId()));
        } catch (Exception e) {
        }
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
