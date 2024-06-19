package com.ddukddak.member.model.service;




import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ddukddak.common.config.KakaoConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService {

	private final RestTemplate restTemplate;
	
	private final KakaoConfig kakaoApi;
	
	/** 카카오 로그인 시 토큰 응답
	 *
	 */
	@Override
	public String getAccessToken(String code) {
		
		String tokenUrl = "https://kauth.kakao.com/oauth/token";
		
		// 1. HTTP 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		// 2. 요청 본문 설정 (쿼리 파라미터)
        StringBuilder requestBody = new StringBuilder();
        requestBody.append("grant_type=authorization_code");
        requestBody.append("&client_id=").append(kakaoApi.getKakaoApiKey());
        requestBody.append("&redirect_uri=").append(kakaoApi.getKakaoRedirectUrl());
        requestBody.append("&code=").append(code);
       
        // 3. HttpEntity 객체 생성
        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
		
        // 4. POST 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);
        
        // 5. 응답 처리
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            JsonElement element = JsonParser.parseString(responseBody);
            JsonObject jsonObject = element.getAsJsonObject();
            return jsonObject.get("access_token").getAsString();
        }
        
        
		return null;
	}

	/** 사용자 정보 얻기
	 *
	 */
	@Override
	public Map<String, Object> getUserInfo(String accessToken) {
		
	    String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

	    // 1. HTTP 헤더 설정
	    HttpHeaders headers = new HttpHeaders();
	    headers.setBearerAuth(accessToken);  // 액세스 토큰을 Bearer 토큰으로 설정

	    // 2. HttpEntity 객체 생성
	    HttpEntity<String> request = new HttpEntity<>(headers);  // 요청 헤더를 포함하는 HttpEntity 객체 생성

	    // 3. GET 요청 보내기
	    ResponseEntity<String> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, String.class);  // 사용자 정보 요청을 위한 GET 요청 전송

	    // 4. 응답 처리
	    if (response.getStatusCode() == HttpStatus.OK) {  // 응답 상태 코드가 200 OK인지 확인
	        String responseBody = response.getBody();
	        JsonElement element = JsonParser.parseString(responseBody);
	        JsonObject jsonObject = element.getAsJsonObject();

	        // 사용자 정보를 저장할 맵 생성
	        Map<String, Object> userInfo = new HashMap<>();
	        
	        // 추출하기 전에 필수 항목이 있을 수도 있으니까 확인하고 가져오기
	        
	        // 사용자 ID 추출
	        if (jsonObject.has("id")) {
	            userInfo.put("id", jsonObject.get("id").getAsString());
	        }

	        // 카카오 계정 정보 추출
	        if (jsonObject.has("kakao_account")) {
	            JsonObject kakaoAccount = jsonObject.getAsJsonObject("kakao_account");

	            // 이메일 추출
	            if (kakaoAccount.has("email")) {
	                userInfo.put("email", kakaoAccount.get("email").getAsString());
	            }

	            // 프로필 정보 추출
	            if (kakaoAccount.has("profile")) {
	                JsonObject profile = kakaoAccount.getAsJsonObject("profile");
	                
	                // 닉네임 추출
	                if (profile.has("nickname")) {
	                    userInfo.put("nickname", profile.get("nickname").getAsString());
	                }
	                
	                // 프로필 이미지 URL 추출
	                if (profile.has("profile_image_url")) {
	                    userInfo.put("profile_image", profile.get("profile_image_url").getAsString());
	                }
	            }
	        }
	        
	        return userInfo;  // 사용자 정보를 담은 맵 반환
	    }

	    return null;  // 응답 상태 코드가 200 OK가 아니면 null 반환

	}
		
}
