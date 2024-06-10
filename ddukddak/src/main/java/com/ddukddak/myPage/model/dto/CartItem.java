package com.ddukddak.myPage.model.dto;



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
	private int productNo;
	private String productName;
	private String optionName; //옵션명 mapper에서 조합하기
	private int productPrice;
	private int productCount;

	// 상품 썸네일 이미지
	private int uploadImgmgNo;
	private String uploadImgPath;
	private String uploadImgOgName;
	private String uploadImgRename;
	private int uploadImgOrder;
	private String category;
}
