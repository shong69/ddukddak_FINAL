package com.ddukddak.common.handler;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.ddukddak.common.chatting.model.dto.Message;
import com.ddukddak.common.chatting.model.service.ChattingService;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.partner.model.dto.Partner;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChattingWebsocketHandler extends TextWebSocketHandler{

	private final ChattingService service;
	private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
	
	//클라이언트와 연결 후 통신 준비 시 실행됨
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		
		log.info("{} 연결됨",session.getId()); //누구랑 연결됐는지 로그로 남기기
	}
	
	//클라이언트와 통신 끝나고 실행
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
		
		log.info("{} 연결 끝", session.getId());
	}
	
	
	//클라이언트에게 메세지 받은 경우
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		Message msg = objectMapper.readValue(message.getPayload(), Message.class);//getPayload 탑제된 데이터 꺼내줌
		
		log.info("msg : {}", msg);
		
		//메세지 보내기
		int result = service.insertMessage(msg);
		if(result>0) {
			
			//메세지 전송 시간을 세팅하고 클에게 전하기
			SimpleDateFormat sdf = new SimpleDateFormat("yyy.MM.dd hh:mm");
			
			msg.setSendTime(sdf.format(new Date())); //msg에 현재 시간 세팅
			
			//채팅하고 있는 상대방을 꺼내오기(전역변수로 선언된 sessions에는 접속중인 모든 회원의 세션 정보가 담겨 있음)
			for(WebSocketSession s : sessions) {
				
				//가로챈 세션 꺼내기
				HttpSession temp = (HttpSession)s.getAttributes().get("session"); 
				//s에서 session이란 어트리뷰트를 가진 거 꺼내고 캐스팅 하기
				
				//로그인된 회원 정보 중 회원 번호를 꺼내오기
				Member loginMember = ((Member)temp.getAttribute("loginMember"));
				Partner loginPartner = ((Partner)temp.getAttribute("loginPartnerMember"));
				

				//로그인 상태인 회원 중 targetNo가 일치하는 회원에게 메시지 전달하기
//				if(loginMemberNo == msg.getTargetNo() || loginMemberNo == msg.getSenderNo()) {
//					//보낸 사람이거나 받은 대상인 경우 메세지를 전달한다
//					
//					//다시 DTO Object를 JSON으로 변환하여 js보내기
//					String jsonData = objectMapper.writeValueAsString(msg);
//					
//					s.sendMessage(new TextMessage(jsonData));
//				}

				//로그인 상태인 회원이 해당 메시지의 수신자 or 발신자인지 체크
				boolean isSender;
				boolean isTarget;
				if(loginMember != null) {
					int loginMemberNo = loginMember.getMemberNo();
					 isSender = loginMemberNo == msg.getSenderNo();
					 isTarget = loginMemberNo == msg.getTargetNo();
				}else {
					int loginPartnerNo = loginPartner.getPartnerNo();
					 isSender = loginPartnerNo == msg.getSenderNo();
					 isTarget = loginPartnerNo == msg.getTargetNo();
				}

				
				//보낸사람이거나 받은 사람인 경우 모두 메세지 전달함
				if(isSender || isTarget) {
					//다시 DTO Object를 JSON으로 변환하여 js보내기
					String jsonData = objectMapper.writeValueAsString(msg);
					s.sendMessage(new TextMessage(jsonData));

				}

			}
						
			
		}
		
	}
}
