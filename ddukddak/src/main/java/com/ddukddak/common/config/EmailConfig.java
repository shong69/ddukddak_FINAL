package com.ddukddak.common.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:/config.properties")
public class EmailConfig {
	
	// @Value : properties에 작성된 내용 중 키가 일치하는 값을 얻어와 필드에 대입
	@Value("${spring.mail.username}")
	private String userName;
	
	@Value("${spring.mail.password}")
	private String password;
	
	@Bean
	public JavaMailSender javaMailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		Properties prop = new Properties();
		prop.setProperty("mail.transport.protocol", "smtp"); // 전송 프로토콜
		prop.setProperty("mail.smtp.auth", "true"); // 인증 사용 여부
		prop.setProperty("mail.smtp.starttls.enable", "true"); // 안전한 연결 활성화 여부
		prop.setProperty("mail.debug", "true"); // 디버그 사용 여부
		prop.setProperty("mail.smtp.ssl.trust","smtp.gmail.com"); // 신뢰할 수 있는 SMTP 서버 호스트 지정
		prop.setProperty("mail.smtp.ssl.protocols","TLSv1.2"); // 버전 설정
		
		
		mailSender.setUsername(userName);
		mailSender.setPassword(password);
		mailSender.setHost("smtp.gmail.com"); // SMTP 서버 호스트 설정
		mailSender.setPort(587); // SMTP 서버 포트 설정
		mailSender.setDefaultEncoding("UTF-8"); // 기본 인코딩 설정
		mailSender.setJavaMailProperties(prop); // 위에 정의한 prop 세팅
		
		return mailSender;
		
	}
}