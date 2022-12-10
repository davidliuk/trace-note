package cn.neud.knownact.oss.controller;

import cn.neud.knownact.common.annotation.LogOperation;
import cn.neud.knownact.model.constant.Constant;
import cn.neud.knownact.common.exception.ErrorCode;
//import cn.neud.knownact.common.page.PageData;
import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.common.validator.ValidatorUtils;
import cn.neud.knownact.oss.cloud.OSSFactory;
import cn.neud.knownact.oss.entity.SysOssEntity;
import cn.neud.knownact.oss.service.SysOssService;
//import cn.neud.modules.sys.service.SysParamsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.*;

/**
 * 文件上传
 *
 * @author David l729641074@163.com
 */
@RestController
@RequestMapping("/oss")
@Api(tags = "文件上传")
public class SysOssController {
    @Resource
    private SysOssService sysOssService;
//    @Resource
//    private SysParamsService sysParamsService;

    private final static String KEY = Constant.CLOUD_STORAGE_CONFIG_KEY;

//    @GetMapping("page")
//    @ApiOperation(value = "分页")
//    // @RequiresPermissions("sys:oss:all")
//    public Result<PageData<SysOssEntity>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
//        PageData<SysOssEntity> page = sysOssService.page(params);
//
//        return new Result<PageData<SysOssEntity>>().ok(page);
//    }

    @PostMapping("upload/{path}")
    @ApiOperation(value = "上传文件")
    // @RequiresPermissions("sys:oss:all")
    public Result<Map<String, Object>> upload(
            @RequestParam("file") MultipartFile file,
            @PathVariable("path") String path
    ) throws Exception {
        if (file.isEmpty()) {
            return new Result<Map<String, Object>>().error(ErrorCode.UPLOAD_FILE_EMPTY);
        }

        //上传文件
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
//		String url = OSSFactory.build().uploadSuffix(file.getBytes(), extension);
        String url = OSSFactory.build().upload(file.getBytes(), path + "/" + UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename());

//		//保存文件信息
//		SysOssEntity ossEntity = new SysOssEntity();
//		ossEntity.setUrl(url);
//		ossEntity.setCreateDate(new Date());
//		sysOssService.insert(ossEntity);

        Map<String, Object> data = new HashMap<>(1);
        data.put("src", url);

        return new Result<Map<String, Object>>().ok(data);
    }

    @DeleteMapping
    @ApiOperation(value = "删除")
    @LogOperation("删除")
    // @RequiresPermissions("sys:oss:all")
    public Result delete(@RequestBody Long[] ids) {
        sysOssService.deleteBatchIds(Arrays.asList(ids));

        return new Result();
    }

}