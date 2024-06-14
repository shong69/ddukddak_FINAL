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
    private String readFlag;
    private int senderNo; //파트너, 멤버
    private int targetNo; //파트너, 멤버
    private int chattingNo;
    private String sendTime;
}
