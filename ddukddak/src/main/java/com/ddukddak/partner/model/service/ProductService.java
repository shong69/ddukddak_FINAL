package com.ddukddak.partner.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductImg;
import com.ddukddak.ecommerce.model.dto.ProductOption;

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
	

	/** 재고상품 삭제
	 * @param productNo
	 * @return
	 */
	int delProduct(int productNo);

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

	/** 판매관리 상품 조회
	 * @param mainSort
	 * @param sort
	 * @param status 
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectApplyList(int mainSort, int sort, String status, int cp);

	/** 판매상태 변경
	 * @param map
	 * @return
	 */
	int changeStatus(Map<String, Object> map);

	/** 판매등록 상품조회
	 * @param productNo
	 * @return
	 */
	Product selectOne(int productNo);

	/** 판매등록 상품 이미지 불러오기
	 * @param productNo
	 * @return
	 */
	List<ProductImg> selectImg(int productNo);

	/** 판매등록 상품 옵션명 불러오기
	 * @param productNo
	 * @return
	 */
	List<ProductOption> seletOptionName(int productNo);

	/** 판매등록 상품 옵션 불러오기
	 * @param productNo
	 * @return
	 */
	List<ProductOption> selectOpion(int productNo);

	/** 상품 판매등록
	 * @param map
	 * @return
	 */
	int updateRegistProduct(Map<String, Object> map);

	/** 이미지 판매등록
	 * @param smallCategory
	 * @param imgList
	 * @return
	 */
	int updateInsertImg(String smallCategory, List<MultipartFile> imgList);



}
