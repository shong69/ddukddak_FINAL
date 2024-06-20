package com.ddukddak.board.model.service;

import java.util.List;
import java.util.Map;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.dto.BoardImg;
import com.ddukddak.board.model.dto.Report;

public interface BoardMainService {

	List<Map<String, Object>> selectBoardTypeList();

	List<BoardImg> selectBoardMainAd();

	List<Board> selectBoard();

	
	/** 게시글 좋아요 체크/해제
	 * @param map
	 * @return count
	 */
	int boardLike(Map<String, Integer> map);

	
	
	/** 신고 등록
	 * @param report
	 * @return result
	 */
	int insertReport(Report report);
}
