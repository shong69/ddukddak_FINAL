package com.ddukddak.liveCommerce.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ddukddak.liveCommerce.model.dto.Chat;

@Controller
@RequestMapping("liveCommerce")
public class LiveCommerceController {
	
	@GetMapping("")
	public String main() {
		return "/liveCommerce/liveCommerceMain";
	}
	
//	/*STOMP(simple text oriented protocol*/
//	@MessageMapping("/sendMessage")
//	@SendTo("/topic/messages")
//	public Chat sendMessage(Chat message) {
//		return message;
//	}
}
