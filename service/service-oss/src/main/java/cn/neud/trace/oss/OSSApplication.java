package cn.neud.trace.oss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableDiscoveryClient
// @EnableFeignClients(basePackages = "cn.neud.trace.client")
@MapperScan("cn.neud.trace.oss.dao")
@ComponentScan(basePackages = {"cn.neud.trace.common", "cn.neud.trace.oss"})
@SpringBootApplication
public class OSSApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OSSApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OSSApplication.class);
	}
}