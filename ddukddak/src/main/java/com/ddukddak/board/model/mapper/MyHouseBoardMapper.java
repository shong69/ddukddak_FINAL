package com.ddukddak.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.dto.BoardImg;

@Mapper
public interface MyHouseBoardMapper {

	
	/** 게시글 작성
	 * @param board
	 * @return result
	 */
	int boardInsert(Board board);

	
	
	/** 게시글 이미지 삽입
	 * @param uploadList
	 * @return result
	 */
	int insertUploadList(List<BoardImg> uploadList);

}
