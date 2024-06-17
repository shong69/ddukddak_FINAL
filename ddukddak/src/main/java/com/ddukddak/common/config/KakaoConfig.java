package com.ddukddak.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@PropertySource("classpath:/config.properties")
@Configuration
public class KakaoConfig {
	
	// 카카오 API 키
	@Value("${kakao.api-key}")
	private String kakaoApiKey;
	
	// 카카오 리다이렉트 URL
	@Value("${kakao.redirect-url}")
	private String kakaoRedirectUrl;
	

    public String getKakaoApiKey() {
        return kakaoApiKey;
    }

    public String getKakaoRedirectUrl() {
        return kakaoRedirectUrl;
    }
    

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
