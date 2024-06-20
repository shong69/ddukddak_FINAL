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
	 * @throws Exception 
	 */
	@Override
	public String getAccessToken() throws Exception {
		
		String tokenUrl = "https://api.iamport.kr/users/getToken";
		
		// 헤더
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
        log.info("키 : " + paymentConfig.getPayApikey());
        log.info("시크릿 : " + paymentConfig.getPaySecret());
		
        String impKey = paymentConfig.getPayApikey();
        String impSecret = paymentConfig.getPaySecret();
        
		// 바디 
		Map<String, String> body = new HashMap<>();
        body.put("imp_key", impKey);
        body.put("imp_secret", impSecret);
		
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
                log.info("AccessToken 추출 : " + accessToken);
                
                return accessToken;
                
            } catch (Exception e) {
            	log.error("Error parsing JSON response: ", e);
            }
        } else {
        	
        	
        	
        	
            log.error("Failed to get token: " + response.getStatusCode());
            throw new Exception("Failed to get token: " + response.getStatusCode());
        }
        
		return null;
		
        
        
		
	}


	/**
	 * 사전 검증
	 */
	@Override
	public Map<String, Object> preparePayment(Map<String, Object> params, String accessToken) {
		
		log.info("사전 검증 토큰 확인 : " + accessToken);
		
		 String prepareUrl = "https://api.iamport.kr/payments/prepare";
		 
		 // 헤더 설정
		 HttpHeaders headers = new HttpHeaders();
		 headers.setContentType(MediaType.APPLICATION_JSON);
		 headers.setBearerAuth(accessToken);  // Authorization 헤더에 토큰 추가
		 
		 String merchantUid = (String) params.get("merchant_uid");
		 Integer amount = (Integer) params.get("amount");
				 
        // 요청 바디 설정
        Map<String, Object> body = new HashMap<>();
        body.put("merchant_uid", merchantUid);
        body.put("amount", amount);
		
        log.info("사전 검증 요청 바디 확인 : " + body);
        
        // 객체 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
		
        // 요청 보내기
        ResponseEntity<String> response = restTemplate.postForEntity(prepareUrl, entity, String.class);
        
        log.info("사전 검증 응답 확인 : " + response);
        
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                // 응답 바디를 JSON으로 파싱
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(response.getBody());
                
                // 에러 코드 확인
                int code = root.path("code").asInt();
                if (code != 0) {
                    log.error("Error code: " + code + ", message: " + root.path("message").asText());
                    return null;
                }
                
                // merchantUid, amount 추출
                String merchantUidResp = root.path("response").path("merchant_uid").asText();
                Integer amountResp = root.path("response").path("amount").asInt();
                
                Map<String, Object> prepareInfo = new HashMap<>();
                
                prepareInfo.put("merchant_uid", merchantUidResp);
                prepareInfo.put("amount", amountResp);
                
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