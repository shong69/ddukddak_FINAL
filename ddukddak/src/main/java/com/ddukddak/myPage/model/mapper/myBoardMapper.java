package com.ddukddak.myPage.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.ddukddak.board.model.dto.Board;

@Mapper
public interface myBoardMapper {

	/**내 집들이 게시글 목록 조회
	 * @param rowBounds 
	 * @param loginMember
	 * @return
	 */
	List<Board> selectMyHouseBoardList(int memberNo, RowBounds rowBounds);

	/**내 노하우 게시글 목록 조회
	 * @param memberNo
	 * @param rowBounds 
	 * @return
	 */
	List<Board> selectMyTipBoardList(int memberNo, RowBounds rowBounds);

	/**좋아요 한 집들이 게시글 목록 조회
	 * @param memberNo
	 * @return
	 */
	List<Board> likeHouseBoardList(int memberNo, RowBounds rowBounds);

	/**좋아요 한 노하우 게시글 목록 조회
	 * @param memberNo
	 * @return
	 */
	List<Board> likeTipBoardList(int memberNo, RowBounds rowBounds);

	/** 내가 쓴 집들이 게시글 개수
	 * @param memberNo
	 * @return
	 */
	int selectMyHouseCount(int memberNo);

	/** 내가 쓴 노하우 게시글 개수
	 * @param memberNo
	 * @return
	 */
	int selectMyTipCount(int memberNo);

	/** 좋아요 한 집들이 개수
	 * @param memberNo
	 * @return
	 */
	int selectLikeHouseCount(int memberNo);

	int selectLikeTipCount(int memberNo);

}
