package com.ddukddak.manager.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

<<<<<<< HEAD
import com.ddukddak.manager.NLPModel;
import com.ddukddak.manager.model.dto.RecommendAnswer;
import com.ddukddak.manager.model.service.ChatbotService;
=======
>>>>>>> 0bb35f145981fb11a9a335e24a95cf5979e17ad2
import com.ddukddak.member.model.dto.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("userChat")
@Slf4j
@RequiredArgsConstructor
public class ChatbotController {
	/*
	 * 질문 카테고리 선택 -> 카테고리에 해당하는 질문 입력
	 * -> 답변 혹은 모르겠어요~ 입력
	 * 제품정보 / 배송 및 반품 / 가격 및 할인 / 주문 및 결제 / 적립금 및 포인트 / 게시글 작성 및 관리 / 댓글 및 좋아요 / 전문가 상담 / 매칭 과정 / 사이트 사용 문제 / 3d홈디자인 기능 / 이벤트 및 프로모션
	 * */
<<<<<<< HEAD
	private final ChatbotService service;
	private final NLPModel nlpModel;
	
=======

	//private final NLPModel nlpModel;
>>>>>>> 0bb35f145981fb11a9a335e24a95cf5979e17ad2
	/** 챗봇 진입
	 * @return
	 */
	@GetMapping("chatbot")
	public String chatWithManager(@SessionAttribute("loginMember") Member loginMember,
			Model model) {
		
		
		return "myPage/chatWithManager";
	}

}
