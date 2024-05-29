package com.ddukddak.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Member {

	private int memberNo;
	private String memberId;
	private String memberEmail;
	private String memberPw;
	private String memberNickname;
	private String memberTel;
	private String memberAddr;
	private int memberPoint;
	private String profileImg;
	private String enrollDate;
	private String memberDelFl;
	private int authority; // 일반 1, 관리자 2
	private String socialLoginType; // 일반 D, 네이버 N, 카카오 K
	
	
}
