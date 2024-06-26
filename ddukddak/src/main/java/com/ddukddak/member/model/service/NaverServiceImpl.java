package com.ddukddak.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ddukddak.common.config.NaverConfig;
import com.ddukddak.member.model.dto.NaverResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverServiceImpl implements NaverService {
	
	private final NaverConfig naverApi;
	private final RestTemplate restTemplate;
	
	// 네이버 정보 반환하기
	@Override
	public Map<String, Object> getNaverUserInfo(String code, String state) {
		
		log.info("네이버 서비스단 code : {} / state : {} ", code, state);
	
        String clientId = naverApi.getNaverClientId();
        String clientSecret = naverApi.getNaverClientSecret();
        
        
        // 1. 토큰 얻어 오기
        String tokenUrl = "https://nid.naver.com/oauth2.0/token";
        
		// 1) HTTP 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
		
		/*
		  https://nid.naver.com/oauth2.0/token?grant_type=authorization_code
		  &client_id=jyvqXeaVOVmV
		  &client_secret=527300A0_COq1_XV33cf
		  &code=EIc5bFrl4RibFls1&state=9kgsGTfH4j7IyAkg
		  
		*/
		
		// 요청 DTO 미사용, 다 얻어왔음

        // 2) 바디 설정
        StringBuilder requestBody = new StringBuilder();
        requestBody.append("grant_type=authorization_code");
        requestBody.append("&client_id=").append(clientId);
        requestBody.append("&client_secret=").append(clientSecret);
        requestBody.append("&code=").append(code);
        requestBody.append("&state=").append(state);
        
        // 3. HttpEntity 객체 생성
        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
		


        try {
        	
            // 4. POST 요청 보내기
            ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);
            
            
            // 5. JSON 응답을 NaverResponse 객체로 변환
            String jsonResponse = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
        	
        	// 얻어온 naverResponse를 DTO로 변환
            NaverResponse naverResponse = objectMapper.readValue(jsonResponse, NaverResponse.class);
            
            log.info("네이버 토큰 응답 결과 DTO 객체 : " + naverResponse);
            
            
            String accessToken = naverResponse.getAccess_token(); 
           
            log.info("네이버 AccessToken : " + accessToken);
            
            // 네이버 사용자 정보 요청 URL
            String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
            
            // 사용자 정보 요청을 위한 헤더 설정
            HttpHeaders userInfoHeaders = new HttpHeaders();
            userInfoHeaders.setBearerAuth(accessToken);
            
            HttpEntity<String> userInfoRequest = new HttpEntity<>(userInfoHeaders);
            
            // 네이버 사용자 정보 요청 보내기
            ResponseEntity<String> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userInfoRequest, String.class);
            log.info("네이버 사용자 정보 응답 변환 전 결과 : " + userInfoResponse);
            
            // 사용자 정보 JSON 응답을 Map으로 변환
            String userInfoJson = userInfoResponse.getBody();
            Map<String, Object> userInfoMap = objectMapper.readValue(userInfoJson, HashMap.class);
            
            log.info("네이버 사용자 정보 응답 변환 후 결과 : " + userInfoMap);
            /*
              {response={id=DMiJp8--wkQCVwIJ0UxyLC6QI0JSmuc1cIG2rEyFcDc, 
              nickname=눈누난나, 
              email=soowagger@naver.com, 
              name=신수민}, 
              resultcode=00, 
              message=success}
             */
            
            return userInfoMap;
           
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return null;
       
        
    }

}
