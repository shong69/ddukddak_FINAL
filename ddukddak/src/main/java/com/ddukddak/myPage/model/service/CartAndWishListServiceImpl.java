package com.ddukddak.myPage.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.member.model.dto.Member;
import com.ddukddak.myPage.model.dto.CartItem;
import com.ddukddak.myPage.model.mapper.CartAndWishListMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CartAndWishListServiceImpl implements CartAndWishListService{

	private final CartAndWishListMapper mapper;

	//장바구니 상품 목록 불러오기
	@Override
	public Map<String, Object> selectCartList(Member loginMember) {
		int memberNo = loginMember.getMemberNo();
		List<CartItem> cartList = mapper.selectCartList(memberNo);
		Map<String, Object> map = new HashMap<>();
		map.put("cartList", cartList);
		
		return map;
	}

	//장바구니 상품 삭제
	@Override
	public int delProduct(Map<String, Object> map) {
		try {
			int result = mapper.deleteProduct(map);
			if(result==1) {
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
		
		for(int oneOption : option) {			
			insertOption = mapper.insertOption(oneOption);
		}
		
		return 0;
	}
	
}
