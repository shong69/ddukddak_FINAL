package com.ddukddak.email.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ddukddak.email.model.service.EmailService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("email")
public class EmailController {
	
	private final EmailService service;
	
	// 회원가입 추후 구현 sendEmail 활용
	
	
//	/** [회원] 아이디 찾기 메일 발송
//	 * @return
//	 */
//	@ResponseBody
//	@PostMapping("findId")	
//	public int findId(@RequestBody String email) {
//		
//		
//		String authKey = service.sendEmail("findId", email);
//		
//		if(authKey != null) {
//			
//			return 1; // 성공
//		}
//		
//		return 0; // 실패
//	}
	
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
