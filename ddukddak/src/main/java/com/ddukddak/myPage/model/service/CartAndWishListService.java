package com.ddukddak.myPage.model.service;

import java.util.List;
import java.util.Map;

import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.myPage.model.dto.CartItem;
import com.ddukddak.myPage.model.dto.Order;

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

	/** 위시리스트 목록조회
	 * @param loginMember
	 * @return
	 */
	Map<String, Object> selectWishList(Member loginMember, int cp);

	/** 주문 목록 조회
	 * @param loginMember
	 * @return
	 */
	List<Order> selectOrderList(Member loginMember);

	/** 구매확정으로 변경
	 * @param orderItemNo
	 * @param orderStatus 
	 * @return
	 */
	int confirmPurchase(int orderItemNo, String orderStatus);

	/**주문취소로 변경
	 * @param orderItemNo
	 * @param orderStatus 
	 * @return
	 */
	int orderDelete(int orderItemNo, String orderStatus);

	/** 리뷰 작성 여부 검사
	 * @param orderItemNo
	 * @param orderNo 
	 * @return
	 */
	int checkReviewWrite(int orderItemNo);

	/**카테고리 값 알아오기
	 * @param productNo
	 * @return
	 */
	Map<String, Object> getProductByNo(int productNo);

}
