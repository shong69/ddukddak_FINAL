package com.ddukddak.partner.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.Product;

public interface ProductService {

	/** 재고등록 재고상품 조회
	 * @param cp 
	 * @param cp2 
	 * @param cp2 
	 * @return
	 */
	Map<String, Object> selectCreateList(int mainSort, int sort, int cp);

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

	/** 상품 재고등록
	 * @param map
	 * @return
	 */
	int registProduct(Map<String, Object> map);

	/** 상품 재고등록 이미지 등록
	 * @param imgList
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	int insertImg(int smallCategory, List<MultipartFile> imgList) throws IllegalStateException, IOException;

	/** 상품 재고등록 옵션 등록
	 * @param item1
	 * @param list
	 * @param list2
	 * @return
	 */
	int insertOpion(String item1, List<String> list, List<String> list2);

}
