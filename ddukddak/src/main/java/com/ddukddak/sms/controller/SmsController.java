package com.ddukddak.sms.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddukddak.sms.model.service.SmsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;


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
    public int sendOne(@RequestBody String toNumber) {
        
    
    	SingleMessageSentResponse response = service.sendSms(toNumber);
    			
    	log.info("response : " + response);
    	
    	// 발송정보 != null == 성공
    	if(response != null) {
    		
    		return 1; // 성공
    		
    	}
    	
    	return 0; // 실패

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