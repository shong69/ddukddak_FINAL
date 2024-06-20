package com.ddukddak.email.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ddukddak.email.model.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@RequestMapping("email")
@Slf4j
public class EmailController {
	
	private final EmailService service;
	
	
	/** 회원가입 메일 발송 
	 * @param email
	 * @return
	 */
	@ResponseBody
	@PostMapping("signup")
	public int signup(@RequestBody String email) {
		
		
		service.sendEmailAsync("signup", email);
		
		
		return 1; // 성공

	}
	
	
    /** 아이디 찾기 메일 발송
     * @return
     */
    @ResponseBody
    @PostMapping("findId")    
    public int findId(@RequestBody String email) {
        // 비동기 메서드 호출로 변경
        service.sendEmailAsync("findId", email);
        
        // 비동기 호출이므로 성공 응답을 즉시 반환
        return 1; // 요청 성공
    }
	
    
    /** 이메일 변경 메일 발송
     * @param email
     * @return
     */
    @ResponseBody
    @PostMapping("updateEmail")
    public ResponseEntity<String> updateEmail(@RequestBody Map<String, String> request) {
    	String email = request.get("email");
    	
    	if(email== null || email.isEmpty()) {
    		return ResponseEntity.badRequest().build();
    	}
    	
    	service.sendEmailAsync("updateEmail", email);
    	return ResponseEntity.ok().build(); //비동기 여부에 대한 http응답 반환
    }
    
	/** 메일 인증키 일치 여부 확인
	 * @param map
	 * @return
	 */
	@ResponseBody
	@PostMapping("checkAuthKey")
	public int checkAuthKey(@RequestBody Map<String, Object> map) {

		
		// 일치 1, 불일치 0
		return service.checkAuthKey(map);
	}
	
	
	
}
