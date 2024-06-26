package com.ddukddak.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NaverRequest {
	
	private String grantType;
	private String clientId;
	private String clientSecret;
	private String code;
	private String state;
	private String refreshToken;
	private String accessToken;
	private String serviceProvider;
		
}
