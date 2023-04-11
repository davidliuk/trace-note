package cn.neud.trace.note.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.neud.trace.note.model.entity.Adornment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author David
 * @since 2021-12-22
 */
public interface AdornmentMapper extends BaseMapper<Adornment> {

    List<Adornment> queryAdornmentOfTrace(@Param("traceId") Long traceId);
}
