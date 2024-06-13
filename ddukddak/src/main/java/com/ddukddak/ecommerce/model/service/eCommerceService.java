package com.ddukddak.ecommerce.model.service;

import java.util.List;
import java.util.Map;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.DetailProduct;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductOption;

public interface eCommerceService {

	/** 오늘의 상품 4개 고르기
	 * @return
	 */
	List<Product> selectProduct(int memberNo);

	/** 베스트상품
	 * @return
	 */
	List<Product> selectBestProduct(int memberNo);

	/** 카테고리별 상품목록 띄우기
	 * @param categoryNo 
	 * @return
	 */
	Map<String, Object> selectProductList(int smallcategoryNo, int cp);

	/** 대분류 카테고리 선택
	 * @return
	 */
	List<Category> selectCategory();

	/** 소분류 카테고리 선택
	 * @param bigcategoryNo
	 * @return
	 */
	List<Category> selectSmallCategory(int bigcategoryNo);

	/** 상품의 상세정보
	 * @param productNo
	 * @return
	 */
	DetailProduct selectOneProduct(int memberNo, int productNo);

	/** 대분류 카테고리 이름
	 * @param bigcategoryNo
	 * @return
	 */
	String selectBigCategory(int bigcategoryNo);

	/** 카테고리별 베스트상품 선택
	 * @param categoryName
	 * @return
	 */
	List<Product> BestProduct(int categoryNo);

	/** 상품별 추천상품 출력
	 * @return
	 */
	List<Product> selectRecProduct(int memberNo, int productNo, int smallcategoryNo);

	/** 상품의 옵션개수 출력
	 * @param productNo
	 * @return
	 */
	List<ProductOption> selectOptionListName(int productNo);

	/** 검색으로 찾은 상품리스트
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> searchList(int memberNo, String query, int cp);

	/** 검색 없이 정렬순서 바꾸기
	 * @param smallcategoryNo
	 * @param cp
	 * @param sort
	 * @return
	 */
	Map<String, Object> selectProductList(int memberNo, int smallcategoryNo, int cp, int sort);

	/** 검색한 상품 정렬순서 바꾸기
	 * @param query
	 * @param cp
	 * @param sort
	 * @return
	 */
	Map<String, Object> searchList(int memberNo, String query, int cp, int sort);

	/** 판매사 페이지용 소분류 카테고리 조회
	 * @return
	 */
	List<Category> selectSmallCategory();

}
