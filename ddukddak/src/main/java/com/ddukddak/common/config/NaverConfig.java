package com.ddukddak.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/config.properties")
@Configuration
public class NaverConfig {
	
	@Value("${naver.client-id}")
	private String naverClientId;
	
	@Value("${naver.client-secret}")
	private String naverClientSecret;

	@Value("${naver.redirect-url}")
	private String naverRedirectUrl;
	
	
	public String getNaverClientId() {
		return naverClientId;
	}
	
	public String getNaverClientSecret() {
		return naverClientSecret;
	}
	
	public String getNaverRedirectUrl() {
		return naverRedirectUrl;
	}
	
}
