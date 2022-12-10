package cn.neud.knownact.feed.service;

import cn.neud.knownact.common.service.CrudService;
import cn.neud.knownact.model.dto.FeedDTO;
import cn.neud.knownact.model.entity.FeedEntity;
import org.springframework.scheduling.annotation.Async;

/**
 * @author David l729641074@163.com
 * @since 1.0.0 2022-12-03
 */
public interface FeedService extends CrudService<FeedEntity, FeedDTO> {

    void train() throws Exception;

}