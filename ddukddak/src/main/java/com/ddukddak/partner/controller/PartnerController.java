package com.ddukddak.partner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("partner")
public class PartnerController {

	@GetMapping("login")
	public String partnerLogin() {
		
		return "/partner/login";
	}
	
	
	/** 파트너 회원가입
	 * @param choice : 1 : 시공사 / 2 : 판매사
	 * @param model
	 * @return
	 */
	@GetMapping("signup")
	public String partnerSignup(
						@RequestParam(value = "choice", required = false) String choice, 
						Model model) {
		
		model.addAttribute("choice", choice);
		
		return "partner/signup";
	}
	
}
