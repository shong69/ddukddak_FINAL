package com.ddukddak.partner.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductImg;
import com.ddukddak.ecommerce.model.dto.ProductOption;
import com.ddukddak.ecommerce.model.dto.QNA;

@Mapper
public interface ProductMapper {
	
	/** 재고등록 재고상품 개수조회
	 * @param productCount 
	 * @return
	 */
	int selectCreateListCount(int productCount);
	
	/** 재고등록 재고상품 조회
	 * @param partnerNo 
	 * @param rowBounds 
	 * @return
	 */
	List<Product> selectCreateList(int partnerNo, RowBounds rowBounds);
	
	/** 재고등록 재고상품 개수조회 메인카테고리
	 * @return
	 */
	int selectCreateListCountMainSort(Map<String, Object> newMap);
	
	/** 재고등록 재고상품 조회 메인카테고리
	 * @param rowBounds 
	 * @return
	 */
	List<Product> selectCreateListMainSort(Map<String, Object> newMap, RowBounds rowBounds);

	/** 재고등록 재고상품 개수조회 카테고리
	 * @return
	 */
	int selectCreateListCountSort(Map<String, Object> newMap);

	/** 재고등록 재고상품 조회 카테고리
	 * @param rowBounds 
	 * @return
	 */
	List<Product> selectCreateListSort(Map<String, Object> newMap, RowBounds rowBounds);

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
	 * @param partnerNo 
	 * @return
	 */
	int selectApplyListCount(int partnerNo);

	/** 판매관리 판매상품 조회
	 * @param partnerNo 
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectApplyList(int partnerNo, RowBounds rowBounds);
	
	/** 판매관리 판매상품 개수조회 상태
	 * @return
	 */
	int selectApplyListCountStatus(Map<String, Object> newMap);
	
	/** 판매관리 판매상품 조회 상태
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectApplyListStatus(Map<String, Object> newMap, RowBounds rowBounds);

	/** 판매관리 판매상품 개수조회 메인카테고리
	 * @param newMap
	 * @return
	 */
	int selectApplyListCountMainSort(Map<String, Object> newMap);

	/** 판매관리 판매상품 조회 메인카테고리
	 * @param newMap
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectApplyListMainSort(Map<String, Object> newMap, RowBounds rowBounds);
	
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
	 * @param newMap
	 * @return
	 */
	int selectApplyListCountSort(Map<String, Object> newMap);

	/** 판매관리 판매상품 조회 카테고리
	 * @param newMap
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectApplyListSort(Map<String, Object> newMap, RowBounds rowBounds);
	
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

	
	/** 접수관리 페이지네이션을 위한 카운트
	 * @param partnerNo
	 * @return
	 */
	int selectReceiptListCount(int partnerNo);

	/** 접수관리 조회하기
	 * @param partnerNo
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectReceiptList(int partnerNo, RowBounds rowBounds);


	/** 전체 문의내역 리스트 개수
	 * @return
	 */
	int qnaListCount();

	/** 전체 문의내역 리스트 가져오기
	 * @return
	 */
	List<QNA> selectQna(RowBounds rowBounds);

	/** 문의답변 넣기
	 * @param map
	 * @return
	 */
	int insertQnaAnswer(Map<String, Object> obj);


	
	/** 젭수관리 대분류
	 * @param newMap
	 * @param rowBounds
	 * @return
	 */

	int selectReceiptListCountMainSort(Map<String, Object> newMap);

	List<Product> selectReceiptListMainSort(Map<String, Object> newMap, RowBounds rowBounds);

	int selectReceiptListCountStatus(Map<String, Object> newMap);

	List<Product> selectReceiptListStatus(Map<String, Object> newMap, RowBounds rowBounds);

	int selectReceiptListCountMainSortStatus(Map<String, Object> newMap);

	List<Product> selectReceiptListMainSortStatus(Map<String, Object> newMap, RowBounds rowBounds);

	int selectReceiptListCountSort(Map<String, Object> newMap);
	
	List<Product> selectReceiptListSort(Map<String, Object> newMap, RowBounds rowBounds);


	int acceptReceipt(Map<String, Object> newMap);





}
