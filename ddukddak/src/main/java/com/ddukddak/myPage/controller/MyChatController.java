package com.ddukddak.myPage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ddukddak.common.chatting.model.dto.ChattingRoom;
import com.ddukddak.common.chatting.model.service.ChattingService;
import com.ddukddak.member.model.dto.Member;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("myChat")
@RequiredArgsConstructor
public class MyChatController {
	
	private final ChattingService service;
	
	/** 채팅 목록 조회 및 페이지 전환
	 * @return
	 */
	@GetMapping("chatWithInter")
	public String chatWithInter(@SessionAttribute("loginMember") Member loginMember,
			Model model) {
		Map<String, Object> map = new HashMap<>();
		int loginMemberNo = loginMember.getMemberNo();
		map.put("isMember", "MEMBER");
		map.put("memberNo", loginMemberNo);
		List<ChattingRoom> roomList = service.selectRoomList(map);
		
		
		return "myPage/chatWithInter";
	}


	/** 챗봇 진입
	 * @return
	 */
	@GetMapping("chatbot")
	public String chatWithManager(@SessionAttribute("loginMember") Member loginMember,
			Model model) {
		
		
		return "myPage/chatWithManager";
	}
	
	@PostMapping("orderInfo")
	@ResponseBody
	public String orderInfo(@SessionAttribute("loginMember") Member loginMember,
							@RequestBody Map<String, String> map) {
		return service.orderInfo(map.get("value"));
	}

}
