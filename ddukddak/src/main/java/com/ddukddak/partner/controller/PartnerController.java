package com.ddukddak.partner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("partner")
public class PartnerController {

	@GetMapping("login")
	public String partnerLogin() {
		
		return "/partner/login";
	}
	
}
