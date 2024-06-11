package com.ddukddak.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.dto.BoardImg;

@Mapper
public interface BoardMainMapper {

	List<Map<String, Object>> selectBoardTypeList();

	List<BoardImg> selectBoardMainAd();

	List<Board> selectBoard();

}
