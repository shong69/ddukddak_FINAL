package com.ddukddak.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NaverResponse {
	private String accessToken;
	private String refreshToken;
	private Integer expiresIn;
	private String error;
	private String errorDescription;	
}
