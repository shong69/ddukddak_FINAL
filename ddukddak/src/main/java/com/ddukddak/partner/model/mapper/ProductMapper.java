package com.ddukddak.partner.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductImg;
import com.ddukddak.ecommerce.model.dto.ProductOption;

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

	/** 재고상품 삭제
	 * @param productNo
	 * @return
	 */
	int delProduct(int productNo);
	
	/** 재고상품 삭제 이미지
	 * @param productNo
	 * @return
	 */
	int delProductImg(int productNo);
	
	/** 재고상품 삭제 옵션
	 * @param productNo
	 * @return
	 */
	int delProductOption(int productNo);

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

	/** 판매등록 재고선택
	 * @param productNo
	 * @return
	 */
	Product selectOne(int productNo);

	/** 판매등록 상품 이미지 조회
	 * @param productNo
	 * @return
	 */
	List<ProductImg> selectImg(int productNo);

	/** 판매등록 상품 옵션명 조회
	 * @param productNo
	 * @return
	 */
	List<ProductOption> selectOptionName(int productNo);

	/** 판매등록 상품 옵션 조회
	 * @param productNo
	 * @return
	 */
	List<ProductOption> selectOption(int productNo);

	/** 상품 판매등록
	 * @param map
	 * @return
	 */
	int updateRegistProduct(Map<String, Object> map);

	/** 대표사진 업로드
	 * @param map
	 * @return
	 */
	int updateThumbnail(Map<String, Object> map);

	/** 상세사진 삭제
	 * @param imgNo
	 * @return
	 */
	int delProductImg2(int imgNo);

	/** 상세사진 업로드
	 * @param map
	 * @return
	 */
	int insertImg2(Map<String, Object> map);

	/** 옵션 비우기
	 * @param productNo
	 * @return
	 */
	int delOption(String productNo);

	/** 새 옵션 추가
	 * @param map
	 * @return
	 */
	int insertOption2(Map<String, Object> map);

	/** 수정상품 등록
	 * @param map
	 * @return
	 */
	int modifyRegistProduct(Map<String, Object> map);

	
	int selectReceiptListCount();



}
