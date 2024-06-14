package com.ddukddak.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.board.model.dto.Comment;

@Mapper
public interface CommentMapper {

	
	
	/** 댓글 목록 조회
	 * @param boardNo
	 * @return commentList
	 */
	List<Comment> select(int boardNo);

	
	
	/** 답글 / 댓글 등록
	 * @param comment
	 * @return result
	 */
	int insert(Comment comment);

}
