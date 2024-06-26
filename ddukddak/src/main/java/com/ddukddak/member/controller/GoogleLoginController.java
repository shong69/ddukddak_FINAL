package com.ddukddak.member.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ddukddak.common.config.GoogleConfig;
import com.ddukddak.member.model.service.GoogleService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GoogleLoginController {
	
	private final GoogleService service;
	private final GoogleConfig googleApi;
	
	/** 구글 로그인 요청 보내기
	 * @param response
	 * @throws IOException
	 */	
	@GetMapping("googleLogin")
	public String googleLoginPage() {
		
		String clientId = googleApi.getGoogleClientId();
		String redirectUri = googleApi.getGoogleRedirectUrl();
		
	    // OAuth 2.0 인증 요청 URL을 생성
	    String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?"
	            + "client_id=" + clientId
	            + "&redirect_uri=https://ddukddak.store/oauth/googleCallback"
	            + "&response_type=code"
	            + "&scope=email%20profile%20openid"
	            + "&access_type=offline"
	            + "&prompt=select_account";
	    
	    log.info("clientId : " + clientId);
	    log.info("redirectUri : " + redirectUri);
	    log.info("OAuth 2.0 요청 URL: " + reqUrl);
	    
	    // 생성된 URL로 리디렉션
	    return "redirect:" + reqUrl;
	}
	
	/** 구글 로그인 콜백 처리 - 로그인, 회원가입 관련 로직은 멤버 쪽에서 
	 * @param code
	 * @param model
	 * @return
	 */
	@GetMapping("oauth/googleCallback")
	public String googleLogin(@RequestParam("code")String code, Model model) {
		
		try {
			
			log.info("콜백 직후 code : " + code);
			// 4/0ATx3LY7619H5iG7qwbHkKoPLIo5waOnYR2sbXHJ5Mxcqton96ad2RMFazvEflj9zytUAbg
			
			// 서비스 단에서 토큰 및 유저 정보 얻기 로직 처리
			Map<String, Object> userInfo = service.getGoogleUserInfo(code);
			
		    log.info("userInfo : " + userInfo);
		    
		    /*
		     {
		     
		     	iss=https://accounts.google.com, 
		     	azp=739886228603-jchth8f6nd964ov8vmf9n9o0qerr4kvo.apps.googleusercontent.com, 
		     	aud=739886228603-jchth8f6nd964ov8vmf9n9o0qerr4kvo.apps.googleusercontent.com, 
		     	sub=104687004116899621948, 
		     	email=soowagger@gmail.com, 
		     	email_verified=true, 
		     	at_hash=ER74l546fjpH8oyN-tZv2w, 
		     	name=신수민, 
		     	picture=https://lh3.googleusercontent.com/a/ACg8ocJaTajP6sJGdvnQpcnGD4JQhri15ROUx5eml2suXn0y0QCmhg=s96-c, 
		     	given_name=수민, 
		     	family_name=신, 
		     	iat=1719083352,
		     	exp=1719086952}
		     
		     
		     */
		    
		    // userInfo 맵에서 필요한 정보를 추출
		    String email = (String) userInfo.get("email");
		    String nickname = (String) userInfo.get("name");
		    String pw = (String) userInfo.get("sub"); // Google 고유 사용자 ID
		    // String picture = (String) userInfo.get("picture");
		    // 굳이 카카오가 아닌데 프로필까지?
			
		    log.info("구글 사용자 정보 : 이메일 : {} / 이름 : {} / 고유정보 : {}", email, nickname, pw);
		    
            // Null 체크(정책상 없겠지만)
            if (email == null || nickname == null || pw == null) {
                throw new IllegalArgumentException("구글 사용자 정보가 포함되지 않았습니다.");
            }
            
            // 이름은 : 구글, 닉네임은 이름으로 넣고 중복 시 카카오 로직이랑 동일하게 ㄱㄱ
            model.addAttribute("email", email);
            model.addAttribute("nickname", nickname);
            model.addAttribute("pw", pw);
		    
		} catch (Exception e) {
	        log.error("Google 로그인 중 예외 발생: " + e);
	        
	    }
		
		return "member/googleCallback";

	}
	
}
