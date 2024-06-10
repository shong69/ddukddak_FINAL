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

	/** 장바구니 상품 삭제
	 * @param map
	 * @return
	 */
	int deleteProduct(Map<String, Object> map);

}
