package com.ddukddak.partner.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.Product;

@Mapper
public interface ProductMapper {

	/** 재고등록 재고상품 조회
	 * @return
	 */
	List<Product> selectCreateList();

	/** 재고상품 판매등록
	 * @param value
	 * @return
	 */
	int sellApplyProduct(Object value);

	/** 대분류에 따른 소분류 조회
	 * @param selectedCategory
	 * @return
	 */
	List<Category> selectSmallCategory(int selectedCategory);

}
