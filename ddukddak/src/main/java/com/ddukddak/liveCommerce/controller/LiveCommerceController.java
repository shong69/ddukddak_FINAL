package com.ddukddak.liveCommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("liveCommerce")
public class LiveCommerceController {
	
	@GetMapping("")
	public String main() {
		return "/liveCommerce/liveCommerceMain";
	}
}
