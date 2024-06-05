package com.ddukddak.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("myChat")
public class MyChatController {

	@GetMapping("chatWithInter")
	public String chatWithInter() {
		return "myPage/chatWithInter";
	}


	@GetMapping("chatWithManager")
	public String chatWithManager() {
		return "myPage/chatWithManager";
	}
}
