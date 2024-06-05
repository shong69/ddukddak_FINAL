package com.ddukddak.board.model.dto;

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
public class Board {

	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriteDate;
	private String boardUpdateDate;
	private int readCount;
	private String boardDelFl;
	private int memberNo;
	
//	커뮤니티 타입 -> 1: 집들이 / 2: 노하우
	private int boardCode;
	
//	커뮤니티 이름
	private String boardName;
	
//	게시글 댓글 등록수
	private int commentCount;
//	게시글 좋아요수
	private int likeCount;
	
	
//  작성자 프로필 이미지
	private String profileImg;
//	게시글 썸네일
	private String thumbnail;
	
	
//	게시글 좋아요 0: 체크 안됨 / 1: 체크 됨
	private int likeCheck;
}
