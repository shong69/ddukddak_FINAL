package com.ddukddak.partner.model.service;

import java.util.List;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.Product;

public interface ProductService {

	/** 재고등록 재고상품 조회
	 * @return
	 */
	List<Product> selectCreateList();

	/** 재고상품 판매등록
	 * @param selectedValues
	 * @return
	 */
	int sellApplyProduct(List<Object> selectedValues);

	/** 대분류에 따른 소분류 조회
	 * @param selectedCategory
	 * @return
	 */
	List<Category> selectSmallCategory(int selectedCategory);

}
