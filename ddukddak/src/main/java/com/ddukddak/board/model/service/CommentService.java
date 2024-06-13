package com.ddukddak.board.model.service;

import java.util.List;

import com.ddukddak.board.model.dto.Comment;

public interface CommentService {

	
	
	/** 댓글 목록 조회
	 * @param boardNo
	 * @return commentList
	 */
	List<Comment> select(int boardNo);

}
