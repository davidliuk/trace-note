package cn.neud.knownact.sms.service.impl;

import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.sms.service.MailService;
import cn.neud.knownact.model.dto.user.UserEmailDTO;
import cn.neud.knownact.sms.utils.MailUtils;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Override
    public Result emailLoginValidate(UserEmailDTO userEmailDTO) {
//       UserEntity user = userDao.selectByEmail(userEmailLoginDTO.getEmail());
        Result result = new Result();
        String verifyCode = MailUtils.sendMail(userEmailDTO.getEmail());
        result.setData(verifyCode);
        result.setMsg("验证码已发送至指定邮箱，请注意查收！");
        return result;
    }

}
