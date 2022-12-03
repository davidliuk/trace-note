package cn.neud.knownact;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("cn.neud.knownact.dao")
@ComponentScan(basePackages = {"cn.neud.knownact.common.redis", "cn.neud.knownact"})
public class KnownactApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnownactApplication.class, args);
    }

}
