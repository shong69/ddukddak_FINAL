package com.ddukddak.myPage.model.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.eCommercePagination;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.myPage.model.dto.CartItem;
import com.ddukddak.myPage.model.mapper.CartAndWishListMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CartAndWishListServiceImpl implements CartAndWishListService{

	private final CartAndWishListMapper mapper;

	//장바구니 상품 목록 불러오기
	@Override
	public List<CartItem> selectCartList(Member loginMember) {
		int memberNo = loginMember.getMemberNo();
		
		List<CartItem> cartList = mapper.selectCartList(memberNo);
		
		for(CartItem item : cartList) {
			List<Integer> optionNo = mapper.selectCartListOptionNo(item.getCartId());
			item.setOptionNo(optionNo);
			
			List<String> optionValue = mapper.selectCartListOptionValue(item.getCartId());
			item.setOptionValue(optionValue);		
		}
		
		return cartList;
	}

	//장바구니 상품 삭제
	@Override
	public int delProduct(int cartId) {
		try {
			int result2 = mapper.deleteProductOption(cartId);
			int result1 = mapper.deleteProduct(cartId);
			
			if(result1 + result2 > 0) {
				return 1;
			}else {
				return -1;
			}
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}

	}

	// 장바구니 상품 추가
	@Override
	public int addCart(Member loginMember, int productNo, List<Integer> option, int quantity) {
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		
		map1.put("memberNo", loginMember.getMemberNo());
		map1.put("productNo", productNo);
		map1.put("quantity", quantity);
		
		int insertCartItem = mapper.insertCartItem(map1);
		
		int insertOption = 0;
		
		for(Object oneOption : option) {	
			insertOption = mapper.insertOption(oneOption);
		}
		
		return insertCartItem + insertOption;
	}

	// 장바구니 상품 수량 변경
	@Override
	public int modifyCount(String cartId, int quantity) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("cartId", cartId);
		map.put("quantity", quantity);
		return mapper.modifyCount(map);
	}

	// 위시리스트 추가
	@Override
	public int addWish(Map<String, Object> obj) {
		return mapper.addWish(obj);
	}

	@Override
	public int delWish(Map<String, Object> obj) {
		int result = mapper.delWish(obj);
		log.info("result1 : " + result);
		return result;
	}

	// 위시리스트 목록조회
	@Override
	public Map<String, Object> selectWishList(Member loginMember, int cp) {
		
		int memberNo = loginMember.getMemberNo();
		
		//1. 전체 게시글 수 조회
		int productCount = mapper.selectWishListCount(memberNo);
		
		//2. pagination 객체 생성하기
		eCommercePagination pagination = new eCommercePagination(cp, productCount);
		//3. 페이지 목록 조회
		int limit = pagination.getLimit(); //제한된 크기
		int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Product> wishList = mapper.selectWishList(memberNo, rowBounds);
		//4. 목록조회 결과 + pagination객체 map으로 묶어서 반환
		
		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("wishList", wishList);

		
		return map;
	}
	
}
