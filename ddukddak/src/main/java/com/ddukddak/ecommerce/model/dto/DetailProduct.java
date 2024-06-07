package com.ddukddak.ecommerce.model.dto;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailProduct {
	
	private int productNo;
	private String productName;
	private int productPrice;
	private String productCreateDate;
	private String productUpdateDate;
	private char productFl;
	private int categoryNo;
	private int bigCategoryNo;
	private int partnerNo;
	
	
	// 상품 이미지
	private List<ProductImg> imgList;
	
	// 상품 옵션
	private List<ProductOption> optionList;

}
