package com.ddukddak.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/config.properties")
@Configuration
public class GoogleConfig {
	
	@Value("${google.client-id}")
	private String googleClientId;
	
	@Value("${google.client-secret}")
	private String googleClientSecret;
	
    public String getGoogleClientId() {
        return googleClientId;
    }

    public String getGoogleClientSecret() {
        return googleClientSecret;
    }
	
}
