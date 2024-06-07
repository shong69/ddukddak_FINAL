package com.ddukddak.sms.controller;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ddukddak.sms.model.service.SmsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("sms")
@Slf4j
@RequiredArgsConstructor
public class SmsController {

    
    private final SmsService service;
    
    /** 아이디 찾기 SMS 발송
     * @param toNumber
     * @return
     */
    @PostMapping("/findId")
    public CompletableFuture<Integer> sendOne(@RequestBody String toNumber) {
        
    
//    	SingleMessageSentResponse response = service.sendSms(toNumber);
//    			
//    	log.info("response : " + response);
//    	
//    	// 발송정보 != null == 성공
//    	if(response != null) {
//    		
//    		return 1; // 성공
//    		
//    	}
//    	
//    	return 0; // 실패
    	
        // 비동기 작업 수행 후 결과를 처리하여 반환
        return service.sendSms(toNumber).thenApply(response -> {
        	
            log.info("response : " + response);
            
            // response가 null이 아니면 1을 반환, null이면 0을 반환
            
            return (response != null) ? 1 : 0;
        });

    }
    
    /** [myPage] 휴대폰 번호 변경:인증번호 발송
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("updatePhoneNum")
    public ResponseEntity<String> updatePhoneNum(@RequestBody Map<String, String> request){
    	String phoneNum = request.get("phoneNum");
    	
    	if(phoneNum== null || phoneNum.isEmpty()) {
    		return ResponseEntity.badRequest().build();
    	}
    	
    	SingleMessageSentResponse response = service.sendSms(phoneNum);
    	if(response != null) return ResponseEntity.ok().build(); //비동기 여부에 대한 http응답 반환
    	else				 return ResponseEntity.badRequest().build();
    }
    
    /** SMS 인증키 일치 여부 확인
     * @param map
     * @return
     */
    @PostMapping("checkSmsAuthKey")
    public int checkSmsAuthKey(@RequestBody Map<String, Object> map) {
    	
    	// 일치 1, 불일치 0
    	return service.checkSmsAuthKey(map);
    }
    
}