package cn.neud.trace.note.service;

import cn.neud.trace.note.model.dto.Result;
import cn.neud.trace.note.model.entity.Trace;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author David
 * @since 2021-12-22
 */
public interface TraceService extends IService<Trace> {

    Result queryById(Long id);

    Result update(Trace trace);

    Result queryTraceByType(Integer typeId, Integer current, Double x, Double y);
}
