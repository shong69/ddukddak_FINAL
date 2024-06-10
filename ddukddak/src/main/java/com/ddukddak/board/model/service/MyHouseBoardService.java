package com.ddukddak.board.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.member.model.dto.Member;

public interface MyHouseBoardService {

	
	/** 집들이 게시글 작성
	 * @param board
	 * @param imgList
	 * @return boardNo
	 */
	int insertBoard(Board board, List<MultipartFile> imgList) throws IOException;
	
	

	/** 집들이 게시판 지정된 페이지 목록 조회
	 * @param boardType
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectMyHouseList(int boardCode, int cp);

	
	
	/** 검색 서비스
	 * @param paramMap
	 * @param cp
	 * @return map
	 */
	Map<String, Object> searchList(Map<String, Object> paramMap, int cp);
	
	
//	/** 집들이 게시판 리스트
//	 * @param boardType
//	 * @return myHouseList
//	 */
//	List<Board> selectMyHouseList(int boardType);





}
