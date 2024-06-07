package com.ddukddak.ecommerce.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.DetailProduct;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductImg;
import com.ddukddak.ecommerce.model.dto.ProductOption;

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
	 * @param categoryNo 
	 * @return
	 */
	List<Product> selectProductList(int smallcategoryNo);

	/** 대분류 카테고리 선택
	 * @return
	 */
	List<Category> selectCategory();

	/** 소분류 카테고리 선택
	 * @param bigcategoryNo
	 * @return
	 */
	List<Category> selectSmallCategory(int bigcategoryNo);

	/** 상품 상세정보
	 * @param productNo
	 * @return
	 */
	DetailProduct selectOneProduct(int productNo);

	/** 상품 상세정보 이미지리스트
	 * @param productNo
	 * @return
	 */
	List<ProductImg> selectImgList(int productNo);

	/** 상품 상세정보 옵션리스트
	 * @param productNo
	 * @return
	 */
	List<ProductOption> selectOptionList(int productNo);

	/** 대분류 카테고리 이름
	 * @param bigcategoryNo
	 * @return
	 */
	String selectBigCategoty(int bigcategoryNo);

	/** 카테고리별 베스트상품 출력
	 * @param map
	 * @return
	 */
	List<Product> BestProduct(int categoryNo);

	/** 카테고리별 추천상품 출력
	 * @param smallcategoryNo
	 * @return
	 */
	List<Product> recProduct(int smallcategoryNo);

	/** 상품의 옵션개수 출력
	 * @param productNo
	 * @return
	 */
	List<ProductOption> selectOptionListName(int productNo);

}
