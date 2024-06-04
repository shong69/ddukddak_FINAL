package com.ddukddak.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("common")
public class CommonController {
	
	@GetMapping("findPw")
	public String commonFindPw() {
		
		return "common/findPw";
	}
	
}
