package com.ddukddak.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/config.properties")
@Configuration
public class PaymentConfig {

	
	@Value("${portone.api-key}")
	private String payApiKey; // 포트원 API 키
	
	@Value("${portone.api-secret}")
	private String paySecret; // 포트원 시크릿
	
	public String getPayApikey() {
		return payApiKey;
	}
	
	public String getPaySecret() {
		return paySecret;
	}
	
	

}
