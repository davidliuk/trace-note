package cn.neud.knownact.sms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableDiscoveryClient
// @EnableFeignClients(basePackages = "cn.neud.knownact.client")
@ComponentScan(basePackages = {"cn.neud.knownact.common", "cn.neud.knownact.sms"})
@SpringBootApplication
public class SMSApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SMSApplication.class, args);
    }

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SMSApplication.class);
	}

}
