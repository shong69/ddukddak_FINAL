package com.ddukddak.myPage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	//채팅 상대 검색
	
	//채팅방 입장(없으면 생성)
	
	
	//채팅방 목록 조회 - 비동기
	
	
	//메세지 조회 - 비동기
	
	//채팅 읽음 표시
	
	
	/** 채팅 목록 조회 및 페이지 전환
	 * @return
	 */
	@GetMapping("chatWithManager")
	public String chatWithManager(@SessionAttribute("loginMember") Member loginMember,
			Model model) {
		
		
		return "myPage/chatWithManager";
	}
	
	
}
