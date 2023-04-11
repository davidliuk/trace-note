package cn.neud.trace.sms.service;

import cn.neud.trace.common.utils.Result;
import cn.neud.trace.model.dto.user.UserEmailDTO;

public interface MailService {

    Result emailLoginValidate(UserEmailDTO userEmailDTO);

}
