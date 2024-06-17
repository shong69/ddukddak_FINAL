package com.ddukddak.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.ddukddak.common.handler.ChattingWebsocketHandler;
import com.ddukddak.common.handler.LiveCommerceWebsocketHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration //서버 실행 시 작성된 메서드를 모두 수행
@EnableWebSocket //웹소켓 활성화 시키는 설정
public class WebSocketConfig implements WebSocketConfigurer{
							//WebSocketConfigurer
	
	//Bean으로 등록된 핸드셰이크인터셉터의 자식타입인 SessionHandshakeInterceptor가 주입된다
	private final HandshakeInterceptor handshakeInterceptor;
	
	
	//채팅 관련 웹소켓 처리 동작이 작성된 객체 의존성 주입
	private final LiveCommerceWebsocketHandler lcHandler;
	private final ChattingWebsocketHandler chattingWebsocketHandler;
	
	
	//웹소켓 핸들러를 등록하는 메서드
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		
		registry.addHandler(chattingWebsocketHandler, "/chattingSock") //js에 적어놨던 경로
				.addInterceptors(handshakeInterceptor)
				.setAllowedOriginPatterns("http://localhost/", 
										"http://127.0.0.1/", 
										"http://192.168.50.200/")
				.withSockJS();
		
		//전체 채팅기능
		registry.addHandler(lcHandler, "/lcSock")
		.addInterceptors(handshakeInterceptor)
		.setAllowedOriginPatterns("http://localhost/", 
								"http://127.0.0.1/", 
								"http://192.168.50.200/")
		.withSockJS();
	}
	
	
	
	
}
