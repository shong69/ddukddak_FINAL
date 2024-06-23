package com.ddukddak.member.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.ddukddak.common.config.GoogleConfig;
import com.ddukddak.member.model.dto.GoogleRequest;
import com.ddukddak.member.model.dto.GoogleResponse;
import com.ddukddak.member.model.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GoogleLoginController {
	
	private static final String nickName = null;
	private final GoogleConfig googleApi;
	private final RestTemplate restTemplate;
	private final MemberService memberService;
	
	// 클라 아이디, 비번 세팅
	
	
	@RequestMapping(value="/googleLogin", method = {RequestMethod.GET, RequestMethod.POST})
    public void loginUrlGoogle(HttpServletResponse response) throws IOException {
        String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleApi.getGoogleClientId()
                + "&redirect_uri=http://localhost/oauth/googleCallback"
                + "&response_type=code"
                + "&scope=email%20profile%20openid"
                + "&access_type=offline"
        		+ "&prompt=select_account";
        response.sendRedirect(reqUrl);
    }
	
	@GetMapping("oauth/googleCallback")
	public String googleLogin(@RequestParam("code")String code, Model model) {
		
		try {
			
			log.info("콜백 직후 code : " + code);
			// 4/0ATx3LY7619H5iG7qwbHkKoPLIo5waOnYR2sbXHJ5Mxcqton96ad2RMFazvEflj9zytUAbg
			
			String tokenUrl = "https://oauth2.googleapis.com/token";
			
			// 요청 DTO에 세팅
			GoogleRequest googleReq = GoogleRequest
										.builder()
										.clientId(googleApi.getGoogleClientId())
										.clientSecret(googleApi.getGoogleClientSecret())
										.code(code)
										.redirectUri("http://localhost/oauth/googleCallback")
										.grantType("authorization_code")
										.build();
			
			// 요청 보내기
			ResponseEntity<GoogleResponse> resultEntity 
					= restTemplate.postForEntity(tokenUrl, googleReq, GoogleResponse.class);
			
			// log.info("구글 응답 요청 resultEntity 확인 : " + resultEntity);
			/* <200 OK OK,GoogleResponse
			 	(access_token=ya29.a0AXooCgsCXumiYgm137uXT4i80nYsXSmRWnKX8qCxTedU5wjZbZuYJwqDkJqJti3RlVoWWQYlIU1YfKRqVx_1rtAri8p7W-0GKz-DMCM1o5AIERyLTmhwpSZakGC2GuRHmgk2HmnLAjicIhAiy2TWLF9IofLsv2OP9gaCgYKAVUSARASFQHGX2MiwjAf813A3XRChukmh8Dpng0169, 
				expires_in=3599, 
				refresh_token=null, 
				scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile openid, 
				token_type=Bearer, 
				id_token=eyJhbGciOiJSUzI1NiIsImtpZCI6IjNkNTgwZjBhZjdhY2U2OThhMGNlZTdmMjMwYmNhNTk0ZGM2ZGJiNTUiLCJ0eXA
				iOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI3Mzk4ODYyMjg2MDMtamNodGg4ZjZuZDk2NG92OHZt
				ZjluOW8wcWVycjRrdm8uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI3Mzk4ODYyMjg2MDMtamNodGg4ZjZuZDk2NG92OHZtZjluOW8wc
				WVycjRrdm8uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDQ2ODcwMDQxMTY4OTk2MjE5NDgiLCJlbWFpbCI6InNvb3dhZ2dlckBnbWFp
				bC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6ImllZ1BieHdhTUFkSjhLOERWLTh3UUEiLCJuYW1lIjoi7Iug7IiY66-8IiwicGljdHVyZ
				SI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FDZzhvY0phVGFqUDZzSkdkdm5RcGNuR0Q0SlFocmkxNVJPVXg1ZW1sMnN1WG4weTBRQ2
				1oZz1zOTYtYyIsImdpdmVuX25hbWUiOiLsiJjrr7wiLCJmYW1pbHlfbmFtZSI6IuyLoCIsImlhdCI6MTcxOTA4NDE2NSwiZXhwIjoxNzE5MDg3NzY1fQ.iUcS0
				zsyDsyjySdq6bSkZDJAsRnD75bJSz0qaCED8zzgXdin8VbjQMu0YX1fCSgm-yHF_obZwYYmsFpOo-TxA9gfoDqKK7SStAvu3IKdJJrG0mfH3t404-mlJ9iZZ3Dv
				76EA2lTTOnLoeIYqbliFoUI3BH9t1ZTyaQ9XNEngGvwrjhNH11Cw_vh7LVtTBNbjsyFVkD9eje2vUMAHiyjT7QuB4XtewItlds_huRyd528OwXeIbjnvtkx5yoY
				IPUwF8-J2MhvAZLrgIoZxjkXz4-JqRpZra3rGHS6Nj32MSF7Ydq4zfTVY3WfI443BcZiOKLF_XNe3O5viOyMc7ivSzg),
			 */
			
			// 이런식으로 id_token이 뿌려지는거 확인
			
			
			// id_token 추출
			// 사용자의 인증 정보를 포함하는 JSON 웹 토큰
			// 사용자 인증을 완료한 후 발급되며 사용자 식별 및 인증 상태를 나타내는 데 사용
			String idToken = resultEntity.getBody().getId_token();
			
		    // id_token 디코딩
			
			// JWT 토큰을 마침표로 분리
		    String[] parts = idToken.split("\\.");
		    
		    // Base64 URL 디코딩하여 페이로드 추출
		    String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
		    log.info("payload :" + payload);
		    // 밑에 userInfo랑 살짝 비교해보면
		    // {"iss":"https://accounts.google.com", ...
		    // 이런 식으로 K:V 형식으로 값이 넘어옴
		    
		    
		    // ObjectMapper를 사용하여 JSON 문자열을 Java Map으로 변환
		    ObjectMapper objectMapper = new ObjectMapper();
		    Map<String, Object> userInfo = objectMapper.readValue(payload, Map.class);
		    
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
		    // userInfo : 
		    
		    // userInfo 맵에서 필요한 정보를 추출
		    String email = (String) userInfo.get("email");
		    String nickname = (String) userInfo.get("name");
		    String pw = (String) userInfo.get("sub"); // Google 고유 사용자 ID
		    // String picture = (String) userInfo.get("picture");
		    // 굳이 카카오가 아닌데 프로필까지?
			
		    log.info("구글 사용자 정보 : 이메일 : {} 이름 : {} 고유정보 : {}", email, nickname, pw);
		    
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
