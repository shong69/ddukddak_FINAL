package com.ddukddak.partner.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.Product;

@Mapper
public interface ProductMapper {
	
	/** 재고등록 재고상품 개수조회
	 * @return
	 */
	int selectCreateListCount();
	
	/** 재고등록 재고상품 조회
	 * @param rowBounds 
	 * @return
	 */
	List<Product> selectCreateList(RowBounds rowBounds);
	
	/** 재고등록 재고상품 개수조회 메인카테고리
	 * @return
	 */
	int selectCreateListCountMainSort(int mainSort);
	
	/** 재고등록 재고상품 조회 메인카테고리
	 * @param rowBounds 
	 * @return
	 */
	List<Product> selectCreateListMainSort(int mainSort, RowBounds rowBounds);

	/** 재고등록 재고상품 개수조회 카테고리
	 * @return
	 */
	int selectCreateListCountSort(int sort);

	/** 재고등록 재고상품 조회 카테고리
	 * @param rowBounds 
	 * @return
	 */
	List<Product> selectCreateListSort(int sort, RowBounds rowBounds);

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

	/** 상품 재고등록
	 * @param map
	 * @return
	 */
	int registProduct(Map<String, Object> map);

	/** 상품 재고 이미지 등록
	 * @param map
	 * @return
	 */
	int insertImg(Map<String, Object> map);

	/** 상품 재고 옵션 등록
	 * @param item1
	 * @param string
	 * @param string2
	 * @return
	 */
	int insertOption(Map<String, Object> map);

	/** 판매관리 판매상품 개수조회
	 * @return
	 */
	int selectApplyListCount();

	/** 판매관리 판매상품 조회
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectApplyList(RowBounds rowBounds);
	
	/** 판매관리 판매상품 개수조회 상태
	 * @return
	 */
	int selectApplyListCountStatus(String status);
	
	/** 판매관리 판매상품 조회 상태
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectApplyListStatus(String status, RowBounds rowBounds);

	/** 판매관리 판매상품 개수조회 메인카테고리
	 * @param mainSort
	 * @return
	 */
	int selectApplyListCountMainSort(int mainSort);

	/** 판매관리 판매상품 조회 메인카테고리
	 * @param mainSort
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectApplyListMainSort(int mainSort, RowBounds rowBounds);
	
	/** 판매관리 판매상품 개수조회 메인카테고리 상태
	 * @param mainSort
	 * @return
	 */
	int selectApplyListCountMainSortStatus(Map<String, Object> newMap);
	
	/** 판매관리 판매상품 조회 메인카테고리 상태
	 * @param mainSort
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectApplyListMainSortStatus(Map<String, Object> newMap, RowBounds rowBounds);

	/** 판매관리 판매상품 개수조회 카테고리
	 * @param sort
	 * @return
	 */
	int selectApplyListCountSort(int sort);

	/** 판매관리 판매상품 조회 카테고리
	 * @param sort
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectApplyListSort(int sort, RowBounds rowBounds);
	
	/** 판매관리 판매상품 개수조회 카테고리 상태
	 * @param sort
	 * @return
	 */
	int selectApplyListCountSortStatus(Map<String, Object> newMap);
	
	/** 판매관리 판매상품 조회 카테고리 상태
	 * @param sort
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectApplyListSortStatus(Map<String, Object> newMap, RowBounds rowBounds);

	/** 판매상태 변경
	 * @param newMap
	 * @return
	 */
	int changeStatus(Map<String, Object> newMap);

}
