package com.ddukddak.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ddukddak.manager.NLPModel;
import com.ddukddak.manager.model.service.ChatbotService;
import com.ddukddak.member.model.dto.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("userChat")
@Slf4j
@RequiredArgsConstructor
public class ChatbotController {
	
	private final ChatbotService service;
	private final NLPModel nlpModel;
	/** 챗봇 진입
	 * @return
	 */
	@GetMapping("chatbot")
	public String chatWithManager(@SessionAttribute("loginMember") Member loginMember,
			Model model) {
		
		
		return "myPage/chatWithManager";
	}
	
	
}
