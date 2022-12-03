package cn.neud.knownact.service;

import cn.neud.knownact.common.service.CrudService;
import cn.neud.knownact.model.dto.FeedDTO;
import cn.neud.knownact.model.entity.FeedEntity;
import org.apache.ibatis.annotations.Update;
import org.springframework.scheduling.annotation.Async;

/**
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
public interface FeedService extends CrudService<FeedEntity, FeedDTO> {

    @Async
    void train() throws Exception;

}