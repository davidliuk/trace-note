package cn.neud.trace.note.service.impl;

import cn.neud.trace.note.service.UserInfoService;
import cn.neud.trace.note.model.entity.UserInfo;
import cn.neud.trace.note.mapper.UserInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author David
 * @since 2021-12-24
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
