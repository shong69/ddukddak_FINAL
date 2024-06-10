package com.ddukddak.myPage.model.service;

import java.util.Map;

import com.ddukddak.member.model.dto.Member;

public interface CartAndWishListService {

	/** 장바구니 상품 목록 불러오기
	 * @param loginMember
	 * @return
	 */
	Map<String, Object> selectCartList(Member loginMember);

	/** 장바구니 상품 삭제
	 * @param map
	 * @return
	 */
	int delProduct(Map<String, Object> map);

}
