package com.ddukddak.board.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.dto.BoardImg;
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
	 * @param sortMethod 
	 * @return map
	 */
	Map<String, Object> selectMyHouseList(int boardCode, String sort, int cp);

	
	
	/** 검색 서비스
	 * @param paramMap
	 * @param cp
	 * @param sortMethod 
	 * @return map
	 */
	Map<String, Object> searchList(int boardCode, String sort, String query, int cp);



	
	/** 게시글 상세 조회
	 * @param boardNo
	 * @return board
	 */
	Board selectBoard(Map<String, Object> map);



	/** 조회수 증가
	 * @param boardNo
	 * @return result
	 */
	int updateReadCount(int boardNo);



	/** 이미지 리스트 조회
	 * @param boardNo
	 * @return imgList
	 */
	List<BoardImg> selectImageList(int boardNo);



	/** 메인페이지용 집들이 게시물 조회
	 * @return
	 */
	List<Board> selectMyHouseList();



	/** 집들이 게시글 삭제
	 * @param boardNo
	 * @return result
	 */
	int deleteMyHouse(int boardNo);



	/** 집들이 수정
	 * @param board
	 * @param images
	 * @return result
	 */
//	int updateMyHouse(Board board, List<MultipartFile> images) throws IllegalStateException, IOException;



	int updateBoard(Board board);



	void updateBoardImages(int boardNo, List<BoardImg> newImages);


}
