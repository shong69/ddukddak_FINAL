package com.ddukddak.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.dto.BoardImg;
import com.ddukddak.board.model.dto.Report;

@Mapper
public interface BoardMainMapper {

	List<Map<String, Object>> selectBoardTypeList();

	List<BoardImg> selectBoardMainAd();

	List<Board> selectBoard();

	
	/** 좋아요 해제
	 * @param map
	 * @return result
	 */ 
	int deleteBoardLike(Map<String, Integer> map);

	
	/** 좋아요 체크
	 * @param map
	 * @return result
	 */
	int insertBoardLike(Map<String, Integer> map);
	

	/** 게시글 좋아요 개수 조회
	 * @param integer
	 * @return count
	 */
	int selectLikeCount(Integer integer);

	
	
	/** 신고 등록
	 * @param report
	 * @return result
	 */
	int insertReport(Report report);

}
