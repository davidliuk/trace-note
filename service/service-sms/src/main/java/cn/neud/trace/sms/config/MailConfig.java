package cn.neud.trace.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public Properties mailProperties() {
        Properties properties = new Properties();
        // 开启debug调试，以便在控制台查看
        properties.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名
        properties.setProperty("mail.host", "smtp.163.com");
        // 发送服务器需要身份验证
        properties.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        properties.setProperty("mail.transport.protocol", "smtp");
        return properties;
    }
}
