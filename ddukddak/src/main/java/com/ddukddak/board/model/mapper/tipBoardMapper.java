package com.ddukddak.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.dto.BoardImg;

@Mapper
public interface tipBoardMapper {

	List<Board> selectTipList(Map<String, Object> map);

	int tipLikeDelete(Map<String, Integer> map);

	int tipLikeInsert(Map<String, Integer> map);

	int selectLikeCount(Integer boardNo);

	int boardInsert(Board board);

	int insertUploadList(List<BoardImg> uploadList);

}
