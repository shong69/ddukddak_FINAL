package com.ddukddak.ecommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QNA {

	private int qnaNo;
	private String qnaTitle;
	private String qnaContent;
	private String qnaAnswer;
	private int memberNo;
	private int partnerNo;
	private String qnaWriteDate;
	private String qnaAnswerDate;
	private char qnaAnswerStatus;
	
	private String memberId;
	private String partnerId;
	
}
