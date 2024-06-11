package com.ddukddak.myPage.model.dto;



import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

	private int cartId;
	private int memberNo;
	private int productNo;
	private String productName;
	private List<Integer> optionNo;
	private List<String> optionValue;
	private int productPrice;
	private int productCount;

	// 상품 썸네일 이미지
	private String productImg;
}
