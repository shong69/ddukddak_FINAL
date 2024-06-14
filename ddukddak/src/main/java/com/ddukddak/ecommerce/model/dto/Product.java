package com.ddukddak.ecommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	
	private int productNo;
	private String productName;
	private int productPrice;
	private String productCreateDate;
	private String productUpdateDate;
	private char productFl;
	private int categoryNo;
	private String categoryName;
	private int bigCategoryNo;
	private String bigCategoryName;
	private int partnerNo;
	private int wish;
	
	
	// 상품 썸네일 이미지
	private int uploadImgmgNo;
	private String uploadImgPath;
	private String uploadImgOgName;
	private String uploadImgRename;
	private int uploadImgOrder;
	private String category;

}
