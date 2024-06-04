package com.ddukddak.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("manager")
public class ManagerController {

	@GetMapping("")
	public String managerMain() {
		return"manager/managerMain";
	}
	
	@GetMapping("chatService")
	public String chatWithMember() {
		return"manager/chatWithMember";
	}
	
	@GetMapping("registerLiveCommerce")
	public String registerLiveCommerce() {
		return"manager/registerLiveCommerce";
	}
}
