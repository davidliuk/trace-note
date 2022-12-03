package cn.neud.knownact.service.impl;

import cn.neud.knownact.service.FeedService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedServiceImplTest {

    @Resource
    FeedService feedService;

    @Test
    void train() throws Exception {
        feedService.train();
        Thread.sleep(10000);
    }
}