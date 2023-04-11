package cn.neud.trace.client.oss;

import cn.neud.trace.common.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@FeignClient(value = "trace-oss", name = "trace-oss", url = "localhost:8084", path = "/api/oss")
@Repository
public interface OSSFeignClient {

    /**
     * 根据排班id获取预约下单数据
     */
    @PostMapping("/upload/{path}")
    @ApiOperation(value = "上传文件")
    public Result<Map<String, Object>> upload(
            @RequestParam("file") MultipartFile file,
            @PathVariable("path") String path
    );

}
