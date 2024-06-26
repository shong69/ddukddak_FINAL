package com.ddukddak.member.model.service;

import java.util.Base64;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ddukddak.common.config.GoogleConfig;
import com.ddukddak.member.model.dto.GoogleRequest;
import com.ddukddak.member.model.dto.GoogleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleServiceImpl implements GoogleService {
		
	private final GoogleConfig googleApi;
	private final RestTemplate restTemplate;
	
	
	/*
								카카오 			vs 				구글
	 	
 	1. 요청 : 형식 및 방식은 동일하지만, 값을 읽는데 차이가 있음
 	* headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
 	- POST 요청의 기본 Content-Type이나 명시하면 좋음
 	- 카카오: 요청 본문을 String으로 구성하여 HttpEntity에 설정함.
 	- 구글: 요청 본문을 MultiValueMap으로 구성하여 더 명확하게 설정함.
	 	
 	2. 응답
	- 		JSON 형식의 엑세스 토큰     		vs		JSON 형식의 액세스 토큰과 id_token
 	- 		토큰을 이용하여 사용자 정보 추출	vs		id_token에 포함된 사용자 정보를 디코딩하여 사용
	 
	
 	*/
	
	// 로그인 유저 정보 구하기
	@Override
	public Map<String, Object> getGoogleUserInfo(String code) throws Exception {

		// 토큰 URL
		String tokenUrl = "https://oauth2.googleapis.com/token";
		
	
		// 1. 요청 DTO에 세팅
		GoogleRequest googleReq = GoogleRequest
									.builder()
									.clientId(googleApi.getGoogleClientId())
									.clientSecret(googleApi.getGoogleClientSecret())
									.code(code)
									.redirectUri(googleApi.getGoogleRedirectUrl())
									.grantType("authorization_code")
									.build();
				
		
		// 2. 요청 보내기
		ResponseEntity<GoogleResponse> responseEntity 
				= restTemplate.postForEntity(tokenUrl, googleReq, GoogleResponse.class);
		
		
		 
		// log.info("구글 응답 요청 resultEntity 확인 : " + resultEntity);
		/* <200 OK OK,GoogleResponse
		 	(access_token=ya29.a0AXooCgsCXumiYgm137uXT4i80nYsXSmRWnKX8qCxTedU5wjZbZuYJwqDkJqJti3RlVoWWQYlIU1YfKRqVx_1rtAri8p7W-0GKz-DMCM1o5AIERyLTmhwpSZakGC2GuRHmgk2HmnLAjicIhAiy2TWLF9IofLsv2OP9gaCgYKAVUSARASFQHGX2MiwjAf813A3XRChukmh8Dpng0169, 
			expires_in=3599, 
			refresh_token=null, 
			scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile openid, 
			token_type=Bearer, 
			id_token=eyJhbGciOiJSUzI1NiIsImtpZCI6IjNkNTgwZjBhZjdhY2U2OThhMGNlZTdmMjMwYmNhNTk0ZGM2ZGJiNTUiLCJ0eXA
			iOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI3Mzk4ODYyMjg2MDMtamNodGg4ZjZuZDk2NG92OHZt
			ZjluOW8wcWVycjRrdm8uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI3Mzk4ODYyMjg2MDMtamNodGg4ZjZuZDk2NG92OHZtZjluOW8wc
			WVycjRrdm8uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDQ2ODcwMDQxMTY4OTk2MjE5NDgiLCJlbWFpbCI6InNvb3dhZ2dlckBnbWFp
			bC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6ImllZ1BieHdhTUFkSjhLOERWLTh3UUEiLCJuYW1lIjoi7Iug7IiY66-8IiwicGljdHVyZ
			SI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FDZzhvY0phVGFqUDZzSkdkdm5RcGNuR0Q0SlFocmkxNVJPVXg1ZW1sMnN1WG4weTBRQ2
			1oZz1zOTYtYyIsImdpdmVuX25hbWUiOiLsiJjrr7wiLCJmYW1pbHlfbmFtZSI6IuyLoCIsImlhdCI6MTcxOTA4NDE2NSwiZXhwIjoxNzE5MDg3NzY1fQ.iUcS0
			zsyDsyjySdq6bSkZDJAsRnD75bJSz0qaCED8zzgXdin8VbjQMu0YX1fCSgm-yHF_obZwYYmsFpOo-TxA9gfoDqKK7SStAvu3IKdJJrG0mfH3t404-mlJ9iZZ3Dv
			76EA2lTTOnLoeIYqbliFoUI3BH9t1ZTyaQ9XNEngGvwrjhNH11Cw_vh7LVtTBNbjsyFVkD9eje2vUMAHiyjT7QuB4XtewItlds_huRyd528OwXeIbjnvtkx5yoY
			IPUwF8-J2MhvAZLrgIoZxjkXz4-JqRpZra3rGHS6Nj32MSF7Ydq4zfTVY3WfI443BcZiOKLF_XNe3O5viOyMc7ivSzg),
		 */
		
		// 이런식으로 id_token이 뿌려지는거 확인
		
		
		// 3. id_token 추출
		// 사용자의 인증 정보를 포함하는 JSON 웹 토큰
		// 사용자 인증을 완료한 후 발급되며 사용자 식별 및 인증 상태를 나타내는 데 사용
		String idToken = responseEntity.getBody().getId_token();
		
	    // 4. id_token 디코딩
		
		// JWT 토큰을 마침표로 분리
	    String[] parts = idToken.split("\\.");
	    
	    // Base64 URL 디코딩하여 페이로드 추출
	    String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
	    log.info("payload :" + payload);
	    // 밑에 userInfo랑 살짝 비교해보면
	    // {"iss":"https://accounts.google.com", ...
	    // 이런 식으로 K:V 형식으로 값이 넘어옴
	    
	    
	    // 5. ObjectMapper를 사용하여 JSON 문자열을 Java Map으로 변환 후 반환
	    ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, Object> userInfo = objectMapper.readValue(payload, Map.class);
		
		
		return userInfo;
	}

}
