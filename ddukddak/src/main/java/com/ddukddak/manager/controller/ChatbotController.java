package com.ddukddak.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ddukddak.manager.NLPModel;
import com.ddukddak.manager.model.dto.InquiryCategory;
import com.ddukddak.manager.model.dto.RecommendAnswer;
import com.ddukddak.manager.model.dto.Request;
import com.ddukddak.manager.model.service.ChatbotService;
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
	
	/** 전체 카테고리
	 * @return
	 */
	@GetMapping("/category")
	@ResponseBody
	public List<InquiryCategory> getCategory() {
		return service.getAllCategory();
	}
	
	/** 답변하기
	 * @param map
	 * @return
	 */
	@PostMapping("/recommend")
	@ResponseBody
	public String getRecommendation(@RequestBody Map<String, Object> map) {
		log.info("카테고리{}",(String)map.get("category"));
		log.info("입력된 문의 {}", (String) map.get("inquiry"));
		
		RecommendAnswer result = service.recommendAnswer((String)map.get("category"),(String) map.get("inquiry"), nlpModel);
		return result.getMessage();
	}
	
	/** 학습하기
	 * @param fr
	 * @return
	 */
//	@ResponseBody
//	@PostMapping("/feedback")
//    public ResponseEntity<String> feedback(@RequestBody  Request fr) {
//        try {
//        	// 피드백 요청으로부터 받은 증상(symptom)과 진료 과(department)를 학습 데이터에 추가
//            nlpModel.addTrainingData(fr.getSymptom(), fr.getDepartment());
//            
//            // 성공적으로 처리되었음을 클라이언트에 알림
//            return ResponseEntity.ok("피드백 수신 및 학습 진행");
//        } catch (Exception e) {
//        	// 처리 중 에러가 발생한 경우, 에러 메시지와 함께 500 상태 코드 반환
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("피드백 과정 오류 발생 : " + e.getMessage());
//        }
//    }
}
