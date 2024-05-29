package com.ddukddak.partner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("partner")
@Controller
@RequiredArgsConstructor
public class InteriorController {
	
	@GetMapping("interiorList")
	public String interiorList() {
		
		return "partner/interior/interiorList";
		
	}

}
