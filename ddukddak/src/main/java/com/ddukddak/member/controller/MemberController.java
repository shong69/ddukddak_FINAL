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
	public String login() {
		
		return "/member/login";
	}
	
}
