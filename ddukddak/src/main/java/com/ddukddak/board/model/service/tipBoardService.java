package com.ddukddak.board.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.board.model.dto.Board;

public interface tipBoardService {

	List<Board> selectTipList(int boardCode, String sort);

	List<Board> searchList(int boardCode, String sort, String query);

	int tipLike(Map<String, Integer> map);

	int insertBoard(Board board, List<MultipartFile> imgList) throws IOException;

}
