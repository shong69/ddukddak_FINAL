package com.ddukddak.partner.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Partner {

	private int partnerNo;
	private String partnerId;
	private String partnerPw;
	private String partnerTel;
	private String partnerBusinessName;
	private String partnerCeoName;
	private String partnerBusinessNum;
	private String enrollDate;
	private String partnerDelFl;
	
	// 파트너 등록 요청 관리자 승인여부
	// -1 : 거절 / 0 : 대기 / 1: 승인
	private int partnerPass;
	
	//	파트너 타입 -> 1: 시공사 /2: 판매사
	private int partnerType;
	
	// 파트너 프로필 이미지
	private String profileImg;
	
}
