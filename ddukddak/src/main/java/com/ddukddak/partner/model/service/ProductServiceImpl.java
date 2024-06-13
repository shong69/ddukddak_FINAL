package com.ddukddak.partner.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.partner.model.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
	
	private final ProductMapper mapper;

	// 재고등록 재고상품 조회
	@Override
	public List<Product> selectCreateList() {
		return mapper.selectCreateList();
	}

	// 재고상품 판매등록
	@Override
	public int sellApplyProduct(List<Object> selectedValues) {
		int result = 0;
		
		for(Object value : selectedValues) {
			result += mapper.sellApplyProduct(value);
		}
		
		return result;
	}

	// 대분류에 따른 소분류 조회
	@Override
	public List<Category> selectSmallCategory(int selectedCategory) {
		return mapper.selectSmallCategory(selectedCategory);
	}

}
