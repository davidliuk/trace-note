package cn.neud.trace.note.service.impl;

import cn.neud.trace.note.service.SeckillAdornmentService;
import cn.neud.trace.note.model.entity.SeckillAdornment;
import cn.neud.trace.note.mapper.SeckillAdornmentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀装扮物表，与装扮物是一对一关系 服务实现类
 * </p>
 *
 * @author David
 * @since 2022-01-04
 */
@Service
public class SeckillAdornmentServiceImpl extends ServiceImpl<SeckillAdornmentMapper, SeckillAdornment> implements SeckillAdornmentService {

}
