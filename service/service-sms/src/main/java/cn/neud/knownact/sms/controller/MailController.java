package cn.neud.knownact.sms.controller;

import cn.neud.knownact.common.annotation.LogOperation;
import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.common.validator.ValidatorUtils;
import cn.neud.knownact.common.validator.group.AddGroup;
import cn.neud.knownact.common.validator.group.DefaultGroup;
import cn.neud.knownact.model.dto.user.UserEmailDTO;

import cn.neud.knownact.sms.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mail")
@Api(tags = "sms")
public class MailController {

    @Resource
    private MailService mailService;

    @GetMapping("code")
    @ApiOperation("邮箱获取验证码")
    @LogOperation("邮箱获取验证码")
//    @RequiresPermissions("user:user:login")
    public Result loginByEmail(@RequestBody UserEmailDTO userEmailDTO) {
        //效验数据
        ValidatorUtils.validateEntity(userEmailDTO, AddGroup.class, DefaultGroup.class);
        return mailService.emailLoginValidate(userEmailDTO);
    }

}
