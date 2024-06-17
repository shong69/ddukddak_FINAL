package com.ddukddak.member.controller;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ddukddak.member.model.dto.Member;
import com.ddukddak.member.model.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NaverLoginController {
	

	private final MemberService service; 
	
	
	/** 네이버 로그인 콜백 HTML 전달
	 * @return
	 */
	@GetMapping("oauth/naverCallback")
	public String naverCallback() {
		return "member/naverCallback";
	}

	@ResponseBody
	@PostMapping("naverLogin")
	public int naverLogin(@RequestBody Map<String, String> map,
						HttpSession session) {
		
		String naverIdKey = map.get("id");
		String naverName = map.get("name");
		String naverEmail = map.get("email");
		
		// 이메일로 가입한 멤버 객체 찾기
		Member member = service.findMemberByEmail(naverEmail);
		
		log.info("Find Member : " + member);
		
		// 1. 멤버가 있다면, 회원가입 or 로그인 처리
		if(member != null) {
			
			if(member.getSocialLoginType().equals("N")) {
				
				// 네이버 로그인 가입자인 경우 로그인 세션에 실어버리기
				session.setAttribute("loginMember", member);
				return 1;
				
			} else {
				
				// 일반 회원 혹은 카카오 가입임 
				return 2;
			}
		// 3. member 테이블이 없다 -> 해당 이메일로 가입된 내용이 없음 -> 가입	
		} else {
			
			
			// 회원 가입 전 데이터 가공
			
			// 1) 아이디
			String uuid = "네이버_" + UUID.randomUUID().toString();
			
			// 2) 이름 길면 6글자 제한
            if (naverName.length() > 6) {
                naverName = naverName.substring(0, 6);
            }
			
			// 3) 닉네임 - 일반 회원 실제 10자리지만 DB는 20자까지 허용
			String dummyNickname= "naver_" + naverName + "_" + RandomStringUtils.randomAlphanumeric(7);
			
			// tel, addr, profile 널 가능
			Member newNaverMember = Member.builder()
									.memberId(uuid) 
									.memberPw(naverIdKey) // 아이디 키
									.memberEmail(naverEmail)
									.memberName(naverName)
									.memberNickname(dummyNickname)						
									.build();
			
			Member signinMember = service.naverSignup(newNaverMember);
			
			if(signinMember != null) {
				
				// 회원 가입 성공
				log.info("네이버 회원 가입 시 정보 : " + signinMember);
				// 로그인 실어주기
				session.setAttribute("loginMember", signinMember);
				
				return 3;
			} 
			
			// 로그인, 중복, 회원가입 다 실패
			return 4;

			
		} 
		
		
	
	}
	
}
