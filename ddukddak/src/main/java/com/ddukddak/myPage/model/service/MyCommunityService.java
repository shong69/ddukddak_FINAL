package com.ddukddak.myPage.model.service;

import java.util.Map;

import com.ddukddak.member.model.dto.Member;

public interface MyCommunityService {

	/**내 집들이 게시글 목록 조회
	 * @param loginMember
	 * @param cp 
	 * @return
	 */
	Map<String, Object> selectMyHouseBoardList(Member loginMember, int cp);

	/** 내 노하우 게시글 목록 조회
	 * @param loginMember
	 * @param cp 
	 * @return
	 */
	Map<String, Object> selectMyTipBoardList(Member loginMember, int cp);

	/**좋아요 한 집들이 게시글 목록 조회
	 * @param loginMember
	 * @return
	 */
	Map<String, Object> selectLikeHouseBoardList(Member loginMember, int cp);

	/**좋아요 한 노하우 게시글 목록 조회
	 * @param loginMember
	 * @return
	 */
	Map<String, Object> selectLikeTipBoardList(Member loginMember, int cp);

}
