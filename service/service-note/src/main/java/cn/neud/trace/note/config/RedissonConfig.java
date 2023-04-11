package cn.neud.trace.note.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        // 创建RedissonClient对象
        return Redisson.create(new Config() {{
            // 配置地址及密码
            useSingleServer().setAddress("redis://123.57.29.205:6379");
//                            .setPassword("DavidLiu7");
        }});
    }
}
