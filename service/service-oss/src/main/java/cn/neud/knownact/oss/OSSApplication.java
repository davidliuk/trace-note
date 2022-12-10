package cn.neud.knownact.oss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cn.neud.knownact")
@MapperScan("cn.neud.knownact.oss.dao")
@ComponentScan(basePackages = {"cn.neud.knownact.common", "cn.neud.knownact.oss"})
public class OSSApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OSSApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OSSApplication.class);
	}
}