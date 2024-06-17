package com.ddukddak.member.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ddukddak.common.config.KakaoConfig;
import com.ddukddak.member.model.service.KakaoService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginController {
	
	// APIKEY, 리다이렉트 url 불러오기
	private final KakaoConfig kakaoApi;
	private final KakaoService kakaoService;

	
	/** 카카오 로그인 페이지 이동
	 * @return
	 */
	@GetMapping("kakaoLogin")
	public String kakaoLoginPage() {
        String redirectUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" 
                + kakaoApi.getKakaoApiKey() 
                + "&redirect_uri=" 
                + kakaoApi.getKakaoRedirectUrl();
        return "redirect:" + redirectUrl;
	}
	
	// 인가 코드 받기 요청의 응답은 HTTP 302 리다이렉트되어, redirect_uri에 GET 요청으로 전달됩니다. 
	// 해당 요청은 아래와 같은 쿼리 파라미터를 포함합니다.
	@GetMapping("oauth/kakaoCallback")
	public String kakaoLogin(@RequestParam("code") String code, Model model, HttpSession session) {
		// code : 인가 코드
		
		log.info("code : " + code);
		// code : mScIKV6FiZoC29T_SyfHyJKvDAQ32rpFvbxPRfb3oGpw8TPcPB6jBgAAAAQKPXMXAAABkCIJz9Eh5oEAb4_jFQ
		
		// 2. 토큰 받기
		String accessToken = kakaoService.getAccessToken(code);
		
		// 세션에 토큰 저장
		session.setAttribute("kakaoAccessToken", accessToken);

		
		log.info("accessToken : " + accessToken);
		// accessToken : CS6knOKwh0XOojm4MpjDWL56Cm96dJKhAAAAAQopyWAAAAGQIkDJPh7SOb8w2j0_
		
		// 3. 유저 정보 얻기
		 Map<String, Object> userInfo = kakaoService.getUserInfo(accessToken);
		 
		 log.info("userInfo : " + userInfo); 		 
		 // userInfo : {profile_image=http://k.kakaocdn.net/dn/bgeGJk/btsEuPrnk3N/5qnGFZnjjSQ5lkiOcjoBtK/img_640x640.jpg, nickname=sm, id=3529573602, email=soowagger@naver.com}
		 // 프로필 기본 이미지 -> http://t1.kakaocdn.net/account_images/default_profile.jpeg.twg.thumb.R640x640
		 // 카카오톡 닉네임은 20글자 제한이고, 바꾸니까 바로 변경되서 날라옴
		 
		 if(userInfo != null) {
			 
//			 String email = userInfo.get("email").toString();
//			 String nickName = userInfo.get("nickname").toString();
//			 String pw = userInfo.get("id").toString(); // 고유 아이디는 비밀번호로 이용
//			 String profileImg = userInfo.containsKey("profile_image") ? userInfo.get("profile_image").toString() : null;
			 
			 String email = (String) userInfo.get("email");
			 String nickName = (String) userInfo.get("nickname");
			 String pw = (String) userInfo.get("id"); // 고유 아이디는 비밀번호로 이용
			 String profileImg = userInfo.containsKey("profile_image") ? (String) userInfo.get("profile_image") : null;
			 // 프로필 이미지는 선택이기 때문에 사용자 동의 거부 시 null로 대체
			 
			 
	         model.addAttribute("email", email);
	         model.addAttribute("nickname", nickName);
	         model.addAttribute("profileImage", profileImg);
	         model.addAttribute("pw", pw);
	         
		 } else {
			 log.error("카카오 컨트롤러 : 사용자 정보 얻기 실패");
	     }
		 
		 
		return "member/kakaoCallback";
	}


		
		
	
	
}
