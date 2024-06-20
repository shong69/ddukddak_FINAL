package com.ddukddak.myPage.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.board.model.dto.Board;

@Mapper
public interface myBoardMapper {

	/**내 집들이 게시글 목록 조회
	 * @param loginMember
	 * @return
	 */
	List<Board> selectMyHouseBoardList(int memberNo);

	/**내 노하우 게시글 목록 조회
	 * @param memberNo
	 * @return
	 */
	List<Board> selectMyTipBoardList(int memberNo);

	/**좋아요 한 집들이 게시글 목록 조회
	 * @param memberNo
	 * @return
	 */
	List<Board> likeHouseBoardList(int memberNo);

	/**좋아요 한 노하우 게시글 목록 조회
	 * @param memberNo
	 * @return
	 */
	List<Board> likeTipBoardList(int memberNo);

}
