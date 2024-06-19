package com.ddukddak.common.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@PropertySource("classpath:/config.properties")
@Configuration
public class PaymentConfig {
	//portone.api-key
			//portone.api-secret
	
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
