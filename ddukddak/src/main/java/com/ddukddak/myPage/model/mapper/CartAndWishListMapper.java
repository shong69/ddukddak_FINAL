package com.ddukddak.myPage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.myPage.model.dto.CartItem;

@Mapper
public interface CartAndWishListMapper {

	/** 장바구니 상품 목록 불러오기
	 * @param memberNo
	 * @return
	 */
	List<CartItem> selectCartList(int memberNo);
	
	/** 장바구니 상품의 옵션아이디 불러오기
	 * @param cartId
	 * @return
	 */
	List<Integer> selectCartListOptionNo(int cartId);
	
	/** 장바구니 상품의 옵션값 불러오기
	 * @param cartId
	 * @return
	 */
	List<String> selectCartListOptionValue(int cartId);

	/** 장바구니 상품 삭제
	 * @param cartId
	 * @return
	 */
	int deleteProduct(int cartId);
	
	/** 장바구니 상품 옵션 삭제
	 * @param cartId
	 * @return
	 */
	int deleteProductOption(int cartId);

	/** 장바구니 상품 추가
	 * @param map1
	 * @return
	 */
	int insertCartItem(Map<String, Object> map1);

	/** 장바구니 상품 옵션 추가
	 * @param map2
	 * @return
	 */
	int insertOption(Object oneOption);

	/** 장바구니 상품 수량 변경
	 * @param map
	 * @return
	 */
	int modifyCount(Map<String, Object> map);

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
