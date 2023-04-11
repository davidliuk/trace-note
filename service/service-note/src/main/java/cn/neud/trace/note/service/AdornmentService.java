package cn.neud.trace.note.service;

import cn.neud.trace.note.model.dto.Result;
import cn.neud.trace.note.model.entity.Adornment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author David
 * @since 2021-12-22
 */
public interface AdornmentService extends IService<Adornment> {

    Result queryAdornmentOfTrace(Long traceId);

    void addSeckillAdornment(Adornment adornment);
}
