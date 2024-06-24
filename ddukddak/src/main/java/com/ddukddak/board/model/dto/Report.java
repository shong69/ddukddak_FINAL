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
@Builder
@ToString
public class Report {
	
	private int reportNo;				// 신고번호
	private int reportedMemberNo;		// 피신고자 번호
	private int memberNo;				// 신고자 번호
	private String reportReason;		// 신고사유
	private String reportDate;			// 신고날짜
	private int boardNo;				// 게시글 번호
	
	
	private String reportedMemberId;	// 피신고자 아이디
	private String memberId;			// 신고자 아이디
	
}
