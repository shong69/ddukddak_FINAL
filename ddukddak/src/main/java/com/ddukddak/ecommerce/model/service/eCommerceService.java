package com.ddukddak.ecommerce.model.service;

import java.util.List;
import java.util.Map;

import com.ddukddak.ecommerce.model.dto.Product;

public interface eCommerceService {

	/** 오늘의 상품 4개 고르기
	 * @return
	 */
	List<Product> selectProduct();

	/** 베스트상품
	 * @return
	 */
	List<Product> selectBestProduct();

	/** 카테고리별 상품목록 띄우기
	 * @return
	 */
	List<Product> selectProductList();

}
