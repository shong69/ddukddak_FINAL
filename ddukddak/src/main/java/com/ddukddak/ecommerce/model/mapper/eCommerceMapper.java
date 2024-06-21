package com.ddukddak.ecommerce.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.DetailProduct;
import com.ddukddak.ecommerce.model.dto.Orders;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductImg;
import com.ddukddak.ecommerce.model.dto.ProductOption;
import com.ddukddak.ecommerce.model.dto.QNA;
import com.ddukddak.ecommerce.model.dto.Review;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.myPage.model.dto.Order;

@Mapper
public interface eCommerceMapper {

	/** 쇼핑몰 메인페이지의 오늘의 상품 추출
	 * @return
	 */
	List<Product> selectProduct(int memberNo);

	/** 베스트상품
	 * @return
	 */
	List<Product> selectBestProduct(int memberNo);
	
	/** 베스트상품
	 * @param categoryNo 
	 * @return
	 */
	List<Product> selectProductList(int smallcategoryNo, RowBounds rowBounds);

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
	DetailProduct selectOneProduct(Map<String, Object> map);

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
	List<Product> recProduct(Map<String, Object> map);

	/** 상품의 옵션개수 출력
	 * @param productNo
	 * @return
	 */
	List<ProductOption> selectOptionListName(int productNo);
	
	/** 카테고리별 상품개수
	 * @param newMap
	 * @return
	 */
	int selectProductListCount1(int smallcategoryNo);

	/** 카테고리별 상품개수
	 * @param newMap
	 * @return
	 */
	int selectProductListCount(Map<String, Object> newMap);
	
	/** 검색으로 찾은 상품개수
	 * @param paramMap
	 * @return
	 */
	int getSearchCount1(String query);

	/** 검색으로 찾은 상품개수
	 * @param paramMap
	 * @return
	 */
	int getSearchCount(Map<String, Object> newMap);
	
	/** 검색으로 찾은 상품 리스트
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectSearchList1(Map<String, Object> newMap, RowBounds rowBounds);

	/** 검색으로 찾은 상품 리스트
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectSearchList(Map<String, Object> newMap, RowBounds rowBounds);

	/** 검색 없이 정렬순서 바꾸기
	 * @param newMap
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectProductListOrder(Map<String, Object> newMap, RowBounds rowBounds);

	/** 검색으로 찾은 상품 정렬순서 바꾸기
	 * @param newMap
	 * @param rowBounds
	 * @return
	 */
	List<Product> selectSearchListOrder(Map<String, Object> newMap, RowBounds rowBounds);

	/** 판매사 페이지용 소분류 카테고리 조회
	 * @return
	 */
	List<Category> selectSmallCategory2();

	 
	/** 멤버 찾기
	 * @param memberNo
	 * @return
	 */
	Member selectMember(int memberNo);
	
	
	/** 주문 생성
	 * @param order
	 * @return
	 */
	int createOrder(Orders order);


	/** qna 입력
	 * @param obj
	 * @return
	 */
	int insertQna(Map<String, Object> obj);

	/**리뷰 등록하기
	 * @param map
	 * @return 
	 */
	int postReview(Map<String, Object> map);
	//reviewNo 알아오기
	int getReviewNo(Map<String, Object> map);

	/** 리뷰 리스트 불러오기
	 * @param productNo
	 * @return
	 */
	List<Review> selectReviewList(int productNo);

	/** 리뷰 작성 가능한 주문 상품 알아오기
	 * @param map
	 * @return
	 */
	List<Order> checkReviewAuth(Map<String, Object> map);


	/** 리뷰 개수 리턴
	 * @param productNo
	 * @return
	 */
	int reviewCount(int productNo);

	/** 리뷰 이미지 등록
	 * @param map
	 * @return
	 */
	int insertImgs(Map<String, Object> map);


	/** 모든 qna 보기
	 * @return
	 */
	List<QNA> selectQna();

	/** 내 qna 보기
	 * @return
	 */
	List<QNA> myQna(int memberNo);


	/** DB 사전 검증
	 * @param merchantUid
	 * @return
	 */
	Orders prepareOrder(String merchantUid);

	/** 사전 검증 완료 건 업데이트
	 * @param merchantUid
	 * @return
	 */
	int readyUpdate(String merchantUid);

	/** 사후 검증 완료 건 최종 처리
	 * @param merchantUid
	 * @return
	 */
	int paidUpdate(String merchantUid);

	/**리뷰 이미지들 가져오기
	 * @param reviewNo
	 * @return
	 */
	List<String> selectReviewImgs(int reviewNo);

	/**리뷰 평점
	 * @param productNo
	 * @return 
	 */
	double avgScore(int productNo);

	/** 수정할 리뷰 불러오기
	 * @param reviewNo
	 * @return
	 */
	Review reloadReview(int reviewNo);




}
