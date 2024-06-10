package com.ddukddak.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

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



//	/** 집들이 게시판 리스트 조회
//	 * @param boardType
//	 * @return myHouseList
//	 */
//	List<Board> selectMyHouseList(int boardType);



	/** 게시글 수 조회
	 * @param boardType
	 * @return listCount
	 */
	int getListCount(int boardCode);



	/** 집들이 게시판의 지정된 페이지 목록 조회
	 * @param boardType
	 * @param rowBounds
	 * @return myHoustList
	 */
	List<Board> selectMyHouseList(int boardCode, RowBounds rowBounds);



	/** 검색 결과에 맞는 게시글 수 조회
	 * @param paramMap
	 * @return count
	 */
	int getSearchCount(Map<String, Object> paramMap);



	/** 검색 결과 목록 조회
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<Board> selectSearchList(Map<String, Object> paramMap, RowBounds rowBounds);


}
