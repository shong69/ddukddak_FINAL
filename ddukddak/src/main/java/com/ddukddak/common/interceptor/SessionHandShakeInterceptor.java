package com.ddukddak.common.interceptor;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.HttpSession;

//웹소켓 동작 전 세션 가로채기
@Component
public class SessionHandShakeInterceptor implements HandshakeInterceptor{

	//핸들러 동작 전 가로채기
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		
		//request가 ServletServerHttpRquest로 다운캐스팅 가능한지
		if(request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)request;
			
			//웹소켓 동작을 요청한 클라이언트의 세션 얻어오기(다운캐스팅 필요)
			HttpSession session = servletRequest.getServletRequest().getSession();
			
			//session handler에 전달
			attributes.put("session", session);
		}
		return true;
	}
	
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		
	}
	
}
