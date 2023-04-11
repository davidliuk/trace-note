package cn.neud.trace.note.service;

import cn.neud.trace.note.model.dto.Result;
import cn.neud.trace.note.model.entity.AdornmentOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author David
 * @since 2021-12-22
 */
public interface AdornmentOrderService extends IService<AdornmentOrder> {

    void cancelOrder(String orderId);

    Result seckillAdornment(Long adornmentId);

    void createAdornmentOrder(AdornmentOrder adornmentOrder);

}
