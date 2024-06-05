package com.ddukddak.ecommerce.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.ecommerce.model.dto.Product;

@Mapper
public interface eCommerceMapper {

	/** 쇼핑몰 메인페이지의 오늘의 상품 추출
	 * @return
	 */
	List<Product> selectProduct();

	/** 베스트상품
	 * @return
	 */
	List<Product> selectBestProduct();
	
	/** 베스트상품
	 * @return
	 */
	List<Product> selectProductList();

}
