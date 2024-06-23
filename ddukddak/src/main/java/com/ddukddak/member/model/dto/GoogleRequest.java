package com.ddukddak.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoogleRequest {
	
	// 승인 요청 매개변수 생성
    private String clientId; 		// 필수, 토큰 교환
    private String clientSecret; 	// 토큰 교환
    private String code;			// 토큰 교환 - 초기 요청에서 반환된 승인 코드
    private String grantType;		// 토큰 교환 - OAuth 2.0 사양에 정의 authorization_code로 설정
    private String redirectUri; 	// 필수 - Google 로그인 후 redirect 위치
    private String responseType;	// 필수 - Google OAuth 2.0 엔드포인트가 인증 코드를 반환하는지 여부
    private String scope;   		// 필수 - OAuth 동의범위
    private String accessType;  	// 권장 - 사용자가 브라우저에 없을 때 애플리케이션이 액세스 토큰을 새로 고칠 수 있는지 여부
    private String state; 			// 권장 - 위조 방지 : redirect_uri는 추측할 수 있으므로 state 값을 사용하면 수신 연결이 인증 요청의 결과임을 확신 
    private String loginHint;   	// 옵션 - 애플리케이션이 인증하려는 사용자를 알고 있는 경우 이 매개변수를 사용하여 Google 인증 서버에 힌트를 제공
    private String prompt;  		// 옵션 - default: 처음으로 액세스를 요청할 때만 사용자에게 메시지가 표시
	
}
