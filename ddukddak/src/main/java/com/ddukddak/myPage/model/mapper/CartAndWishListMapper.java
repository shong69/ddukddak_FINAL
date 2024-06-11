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
	 * @param map
	 * @return
	 */
	int deleteProduct(Map<String, Object> map);

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

	

	

}
