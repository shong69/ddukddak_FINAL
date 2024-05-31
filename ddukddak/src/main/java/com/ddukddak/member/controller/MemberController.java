package com.ddukddak.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ddukddak.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("member")
@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberService service;
	
	@GetMapping("login")
	public String memberLogin() {
		
		return "/member/login";
	}
	

	/** [회원] 회원가입 페이지 이동
	 * @return
	 */
	@GetMapping("signup")
	public String memberSignup() {
		
		return "/member/signup";
	}
	
	
	/** [회원] 아이디 찾기 페이지 이동
	 * @return
	 */
	@GetMapping("findId")
	public String memberFindId() {
		return "member/findId";
	}
	
	/** [회원] 비밀번호 찾기 페이지 이동
	 * @return
	 */
	@GetMapping("findPw")
	public String memberFindPw() {
		return "member/findPw";
	}
}
