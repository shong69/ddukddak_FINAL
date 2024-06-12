package com.ddukddak.myPage.model.service;

import java.util.List;
import java.util.Map;

import com.ddukddak.member.model.dto.Member;
import com.ddukddak.myPage.model.dto.CartItem;

public interface CartAndWishListService {

	/** 장바구니 상품 목록 불러오기
	 * @param loginMember
	 * @return
	 */
	List<CartItem> selectCartList(Member loginMember);

	/** 장바구니 상품 삭제
	 * @param cartId
	 * @return
	 */
	int delProduct(int cartId);

	/** 장바구니 상품 추가
	 * @param productNo
	 * @param option
	 * @param quantity
	 * @return
	 */
	int addCart(Member loginMember, int productNo, List<Integer> option, int quantity);

	/** 장바구니 상품 수량 변경
	 * @param cartId
	 * @param quantity
	 * @return
	 */
	int modifyCount(String cartId, int quantity);

	/** 위시리스트 추가
	 * @param obj
	 * @return
	 */
	int addWish(Map<String, Object> obj);

	/** 위시리스트 삭제
	 * @param obj
	 * @return
	 */
	int delWish(Map<String, Object> obj);

}
