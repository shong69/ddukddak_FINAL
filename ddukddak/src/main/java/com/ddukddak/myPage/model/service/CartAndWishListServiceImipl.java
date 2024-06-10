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
public class CartAndWishListServiceImipl implements CartAndWishListService{

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
	
}
