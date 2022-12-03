package cn.neud.knownact;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.neud.knownact.dao")
public class KnownactApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnownactApplication.class, args);
    }

}
