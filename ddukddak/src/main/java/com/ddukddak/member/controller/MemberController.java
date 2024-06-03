package com.ddukddak.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ddukddak.member.model.dto.Member;
import com.ddukddak.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("member")
@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberService service;
	
	@GetMapping("login")
	public String memberLogin() {
		
		return "member/login";
	}
	

	/** [회원] 회원가입 페이지 이동
	 * @return
	 */
	@GetMapping("signup")
	public String memberSignup() {
		
		return "member/signup";
	}
	
	
	/** [회원] 아이디 찾기 페이지 이동
	 * @return
	 */
	@GetMapping("findId")
	public String memberFindId() {
		return "member/findId";
		
	}
	
	
	/** [회원] 아이디 찾기 결과
	 * @param authType
	 * @return
	 */
	@GetMapping("resultId")
	public String resultId(
					@RequestParam(value = "authType", required = false) String authType,
					@RequestParam("userType") String userType,
					Member inputMember,
					Model model
					) {
		
		model.addAttribute("authType", authType);
		model.addAttribute("userType", userType);
		
		return "/common/resultId";
	}
	
	
	/** [회원] 비밀번호 찾기 페이지 이동
	 * @return
	 */
	@GetMapping("findPw")
	public String memberFindPw() {
		return "member/findPw";
	}
}
