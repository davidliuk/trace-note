package cn.neud.knownact.sms.service;

import cn.neud.knownact.common.utils.Result;
import cn.neud.knownact.model.dto.user.UserEmailDTO;

public interface MailService {

    Result emailLoginValidate(UserEmailDTO userEmailDTO);

}
