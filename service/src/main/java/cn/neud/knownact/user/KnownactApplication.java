package cn.neud.knownact.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("cn.neud.knownact.user.dao")
@EnableAsync
@ComponentScan(basePackages = {"cn.neud.knownact.common", "cn.neud.knownact.user"})
public class KnownactApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnownactApplication.class, args);
    }

}
