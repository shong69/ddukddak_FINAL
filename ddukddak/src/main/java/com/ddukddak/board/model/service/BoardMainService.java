package com.ddukddak.board.model.service;

import java.util.List;
import java.util.Map;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.dto.BoardImg;

public interface BoardMainService {

	List<Map<String, Object>> selectBoardTypeList();

	List<BoardImg> selectBoardMainAd();

	List<Board> selectBoard();
}
