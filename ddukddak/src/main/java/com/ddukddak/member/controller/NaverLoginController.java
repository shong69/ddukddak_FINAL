package com.ddukddak.member.controller;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ddukddak.common.config.NaverConfig;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.member.model.service.MemberService;
import com.ddukddak.member.model.service.NaverService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NaverLoginController {
	

	private final NaverService service;
	private final NaverConfig naverApi;
	
	
	/** 네이버 로그인 콜백 HTML 전달
	 * @return
	 */
	@GetMapping("naverLogin")
	public String naverLoginPage(HttpSession session) {
		
		String clientId = naverApi.getNaverClientId();
		String redirectUrl = naverApi.getNaverRedirectUrl();
		
        // state 값을 UUID를 사용하여 생성
        String state = UUID.randomUUID().toString();
        
        // 생성된 state 값을 세션에 저장
        session.setAttribute("state", state);
		
		String reqUrl = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUrl
                + "&state=" + state;
		
		log.info("clientId :" + clientId);
		log.info("redirectUrl :" + redirectUrl);
		log.info("네이버 reqUrl : " + reqUrl);
		
		return "redirect:" + reqUrl;
	}
	
	@PostMapping("oauth/naverCallback")
	public String naverLogin(@RequestParam("code")String code, Model model, 
							@RequestParam("state") String state, HttpSession session) {
		
		log.info("네이버 code : " + code);
		log.info("네이버 state : " + state);
		
        // 세션에 저장된 state 값과 콜백에서 받은 state 값을 비교하여 유효성 검증
        String sessionState = (String) session.getAttribute("state");
        if (sessionState == null || !sessionState.equals(state)) {
            throw new IllegalStateException("유효하지 않은 상태 검증입니다.");
        }

        // 서비스 단에서 유저 정보 구해오기
        //Map<String, Object> userInfo = service.getNaverUserInfo(code, state);
        
        
        
		
		
		return "member/naverCallback";
	}

	
}
