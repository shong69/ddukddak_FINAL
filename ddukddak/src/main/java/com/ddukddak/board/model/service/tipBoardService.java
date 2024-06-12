package com.ddukddak.board.model.service;

import java.util.List;
import java.util.Map;

import com.ddukddak.board.model.dto.Board;

public interface tipBoardService {

	List<Board> selectTipList(int boardCode, String sort);

	List<Board> searchList(int boardCode, String sort, String query);

}
