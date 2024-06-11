package com.ddukddak.myPage.model.service;

import java.util.List;
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

	/** 장바구니 상품 추가
	 * @param productNo
	 * @param option
	 * @param quantity
	 * @return
	 */
	int addCart(Member loginMember, int productNo, List<Integer> option, int quantity);

}
