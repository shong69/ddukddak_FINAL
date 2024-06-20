package com.ddukddak.ecommerce.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ddukddak.common.config.PaymentConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
	
	private final PaymentConfig paymentConfig;
	private final RestTemplate restTemplate;
	
	
	/**
	 * 토큰 얻기
	 */
	@Override
	public String getAccessToken() {
		
		String tokenUrl = "https://api.iamport.kr/users/getToken";
		
		// 헤더
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		// 바디 
		Map<String, String> body = new HashMap<>();
        body.put("imp_key", paymentConfig.getPayApikey());
        body.put("imp_secret", paymentConfig.getPaySecret());
		
        // HttpEntity 객체 생성
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
		
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, entity, String.class);
        
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                // 응답 바디를 JSON으로 파싱
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(response.getBody());
                
                // access_token 추출
                String accessToken = root.path("response").path("access_token").asText();
                log.info("Access Token: " + accessToken);
                
                return accessToken;
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.error("Failed to get token: " + response.getStatusCode());
        }
		return "토큰 못가져옴";
        
        
		
	}


	/**
	 * 사전 검증
	 */
	@Override
	public Map<String, Object> preparePayment(Map<String, Object> params, String accessToken) {
		
		 String prepareUrl = "https://api.iamport.kr/payments/prepare";
		 
		 // 헤더 설정
		 HttpHeaders headers = new HttpHeaders();
		 headers.setContentType(MediaType.APPLICATION_JSON);
		 headers.setBearerAuth(accessToken);  // Authorization 헤더에 토큰 추가
		 
		 
        // 요청 바디 설정
        Map<String, Object> body = new HashMap<>();
        body.put("merchant_uid", params.get("merchant_uid"));
        body.put("amount", params.get("amount"));
		 
        // 객체 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
		
        // 요청 보내기
        ResponseEntity<String> response = restTemplate.postForEntity(prepareUrl, entity, String.class);
        
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                // 응답 바디를 JSON으로 파싱
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(response.getBody());
                
                // merchantUid, amount 추출
                String merchantUid = root.path("response").path("merchant_uid").asText();
                int amount = root.path("response").path("amount").asInt();
                
                Map<String, Object> prepareInfo = new HashMap<>();
                
                prepareInfo.put("merchant_uid", merchantUid);
                prepareInfo.put("amount", amount);
                
                return prepareInfo;
                
            } catch (Exception e) {
                log.error("Error parsing JSON response: ", e);
                log.error("Response Body: " + response.getBody());
                
            }
        } else {
            log.error("Failed to prepare payment: " + response.getStatusCode());
            log.error("Response Body: " + response.getBody());
            

        }
        
        return null;
		
	}
	
  

}