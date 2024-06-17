package com.ddukddak.common.handler;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LiveCommerceWebsocketHandler extends TextWebSocketHandler{

	private Set<WebSocketSession> sessions 
					= Collections.synchronizedSet(new HashSet<>());

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	
		sessions.add(session);
	}
	
	//클라이언트와 연결이 종료되면 실행
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		//웹소켓 연결이 끊긴 클라이언트 정보를 Set에서 제거하기
		sessions.remove(session);
	}
	
	
	//클라이언트로부터 텍스트 메시지를 받았을 때 실행
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//다수의 클라이언트들에게서 보낸 메세지를 서로 볼 수 있도록 할거임
	
		
		log.info("전달받은 메시지 : {}", message.getPayload()); 
									//getPayload() : 통신 시 탑제된 데이터, 즉 메세지를 말함
		
		
		//전달받은 메세지를 현재 해당 웹소켓에 연결된 모든 클라이언트에게 보내기
		for(WebSocketSession s:sessions) {
			s.sendMessage(message); //세션에 있는 각 클라이언트들에게 메세지를 보내줌
		}
	}
	
}
