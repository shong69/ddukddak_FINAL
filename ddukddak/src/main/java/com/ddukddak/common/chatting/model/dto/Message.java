package com.ddukddak.common.chatting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message {
	private int messageNo;
    private String messageContent;
    private String readFl;
    private int senderNo; //메시지 보낸 회원 번호
    private int targetNo; //메세지 송신 받을 사람 회원번호(파트너 + 일반회원)
    private int chattingNo;
    private String sendTime;
    private String senderType; // 메세지 수신 시 발신자가 일반회원인지 시공사인지 구분
    private String targetType; //수신자 타입
}
