package com.ddukddak.ecommerce.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.DetailProduct;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductImg;
import com.ddukddak.ecommerce.model.dto.ProductOption;
import com.ddukddak.ecommerce.model.dto.Review;
import com.ddukddak.ecommerce.model.dto.eCommercePagination;
import com.ddukddak.ecommerce.model.mapper.eCommerceMapper;
import com.ddukddak.myPage.model.dto.Order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class eCommerceServiceImpl implements eCommerceService{
	
	private final eCommerceMapper mapper;

	// 쇼핑몰 메인페이지 오늘의 상품 고르기
	@Override
	public List<Product> selectProduct(int memberNo) {
		
		List<Product> list = mapper.selectProduct(memberNo);
        
        List<Product> randomList = new ArrayList<Product>();
        
        List<Integer> saveNo = new ArrayList<Integer>();
        
        for(int i = 0; i < 4; i++) {
        	Random random = new Random();
            int randomIndex = random.nextInt(list.size());
            
            if(saveNo.contains(randomIndex)) {
            	i--;
            } else {
            	saveNo.add(randomIndex);
            	
            	randomList.add(list.get(randomIndex));
            }
        	
        }
		
		return randomList;
	}

	// 베스트상품
	@Override
	public List<Product> selectBestProduct(int memberNo) {
		
		List<Product> list = mapper.selectBestProduct(memberNo);
		
		List<Product> bestList = new ArrayList<Product>();
		
		for(int i = 0; i < 4; i++) {
			bestList.add(list.get(i));
        }
		
		return bestList;
	}

	// 카테고리별 상품목록 띄우기
	@Override
	public Map<String, Object> selectProductList(int smallcategoryNo, int cp) {

		//1. 전체 게시글 수 조회
		int productCount = mapper.selectProductListCount1(smallcategoryNo);
		
		//2. pagination 객체 생성하기
		eCommercePagination pagination = new eCommercePagination(cp, productCount);
		//3. 페이지 목록 조회
		int limit = pagination.getLimit(); //제한된 크기
		int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Product> productList = mapper.selectProductList(smallcategoryNo, rowBounds);
		//4. 목록조회 결과 + pagination객체 map으로 묶어서 반환
		
		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("productList", productList);
		
		log.info("productList" + productList);
		
		return map;
	}

	// 대분류 카테고리 선택
	@Override
	public List<Category> selectCategory() {
		return mapper.selectCategory();
	}

	// 소분류 카테고리 선택
	@Override
	public List<Category> selectSmallCategory(int bigcategoryNo) {
		return mapper.selectSmallCategory(bigcategoryNo);
	}

	// 상품의 상세정보
	@Override
	public DetailProduct selectOneProduct(int memberNo, int productNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("memberNo", memberNo);
		map.put("productNo", productNo);
		
		DetailProduct detailProduct = mapper.selectOneProduct(map);
		
		List<ProductImg> imgList = mapper.selectImgList(productNo);
		
		List<ProductOption> optionList = mapper.selectOptionList(productNo);
		
		detailProduct.setImgList(imgList);
		detailProduct.setOptionList(optionList);
		
		return detailProduct;
	}

	// 대분류 카테고리 이름
	@Override
	public String selectBigCategory(int bigcategoryNo) {
		return mapper.selectBigCategoty(bigcategoryNo);
	}

	// 카테고리별 베스트상품 출력
	@Override
	public List<Product> BestProduct(int categoryNo) {
		
		List<Product> list = mapper.BestProduct(categoryNo);
		
		List<Product> bestList = new ArrayList<Product>();
		
		for(int i = 0; i < 4; i++) {
			bestList.add(list.get(i));
        }
		
		return bestList;
	}

	// 상품별 추천상품 출력
	@Override
	public List<Product> selectRecProduct(int memberNo, int productNo, int smallcategoryNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("productNo", productNo);
		map.put("smallcategoryNo", smallcategoryNo);
		map.put("memberNo", memberNo);
		
		List<Product> list = mapper.recProduct(map);
        
        List<Product> randomList = new ArrayList<Product>();
        
        List<Integer> saveNo = new ArrayList<Integer>();
        
        for(int i = 0; i < 4; i++) {
        	Random random = new Random();
            int randomIndex = random.nextInt(list.size());
            
            if(saveNo.contains(randomIndex)) {
            	i--;
            } else {
            	saveNo.add(randomIndex);
            	
            	randomList.add(list.get(randomIndex));
            }
        	
        }
		
		return randomList;
	}

	// 상품의 옵션개수 출력
	@Override
	public List<ProductOption> selectOptionListName(int productNo) {
		return mapper.selectOptionListName(productNo);
	}

	// 검색으로 찾은 상품리스트 출력
	@Override
	public Map<String, Object> searchList(int memberNo, String query, int cp) {
		
		//1.검색조건 맞고, 삭제 안된 게시글 수 조회
		int listCount = mapper.getSearchCount1(query);
		
		//2. + cp 사용해서 Pagination 생성하기
		eCommercePagination pagination = new eCommercePagination(cp, listCount);
		
		//3. 페이지 목록 조회하기
		int limit = pagination.getLimit();
		int offset =(cp-1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		Map<String, Object> newMap = new HashMap<String, Object>();
		
		newMap.put("query", query);
		newMap.put("memberNo", memberNo);
		
		List<Product> productList = mapper.selectSearchList1(newMap, rowBounds);
		
		//4. 목록 조회 결과 + pagination 객체를 map으로 묶어서 결과로 반환
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("productList", productList);
		
		
		return map;
		
	}

	// 검색 없이 정렬순서 바꾸기
	@Override
	public Map<String, Object> selectProductList(int memberNo, int smallcategoryNo, int cp, int sort, int minPrice, int maxPrice) {
		
		Map<String, Object> newMap = new HashMap<String, Object>();
		
		newMap.put("smallcategoryNo", smallcategoryNo);
		newMap.put("sort", sort);
		newMap.put("minPrice", minPrice);
		newMap.put("maxPrice", maxPrice);
		newMap.put("memberNo", memberNo);
		
		log.debug("cp : " + cp);
		//1. 전체 게시글 수 조회
		int productCount = mapper.selectProductListCount(newMap);
		
		//2. pagination 객체 생성하기
		eCommercePagination pagination = new eCommercePagination(cp, productCount);
		//3. 페이지 목록 조회
		int limit = pagination.getLimit(); //제한된 크기
		int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
		
		RowBounds rowBounds = new RowBounds(offset, limit);

		List<Product> productList = mapper.selectProductListOrder(newMap, rowBounds);
		//4. 목록조회 결과 + pagination객체 map으로 묶어서 반환
		
		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("productList", productList);
		
		log.info("productList" + productList);
		
		return map;
	}

	// 검색한 상품 정렬순서 바꾸기
	@Override
	public Map<String, Object> searchList(int memberNo, String query, int cp, int sort, int minPrice, int maxPrice) {
		
		Map<String, Object> newMap = new HashMap<String, Object>();
		
		newMap.put("query", query);
		newMap.put("sort", sort);
		newMap.put("minPrice", minPrice);
		newMap.put("maxPrice", maxPrice);
		newMap.put("memberNo", memberNo);
		
		///1.검색조건 맞고, 삭제 안된 게시글 수 조회
		int listCount = mapper.getSearchCount(newMap);
		
		//2. + cp 사용해서 Pagination 생성하기
		eCommercePagination pagination = new eCommercePagination(cp, listCount);
		
		//3. 페이지 목록 조회하기
		int limit = pagination.getLimit();
		int offset =(cp-1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Product> productList = mapper.selectSearchListOrder(newMap, rowBounds);
		
		//4. 목록 조회 결과 + pagination 객체를 map으로 묶어서 결과로 반환
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("productList", productList);
		
		
		return map;
	}

	// 판매사 페이지용 소분류 카테고리 조회
	@Override
	public List<Category> selectSmallCategory() {
		return mapper.selectSmallCategory2();
	}

	//리뷰 목록 조회하기
	@Override
	public List<Review> selectReviewList(int productNo) {
		List<Review> list = mapper.selectReviewList(productNo);
		return list;
	}

	//해당 상품에 대한 리뷰 작성 가능 개수 조회 -> 드롭다운으로 표시할거임
	@Override
	public List<Order> checkReviewAuth(int productNo, int memberNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("productNo", productNo);
		map.put("memberNo", memberNo);
		return mapper.checkReviewAuth(map);
	}

	//리뷰 등록하기
	@Override
	public Review postReview(Review review) {
		//주문 테이블에서 memberNo와 productNo에 해당하는 orderItemNo 찾아오기(리뷰를 써야 하는 주문상품)
		mapper.postReview(review);

		return review;
	}

	//리뷰 사진 등록하기  
	@Override
	public int insertImgs(int reviewNo, List<MultipartFile> reviewImgs) {
		
		return 0;
	}
	
	//리뷰 삭제하기
	@Override
	public int delReview(int reviewId) {
		// TODO Auto-generated method stub
		return 0;
	}

	//수정할 리뷰 불러오기
	@Override
	public Review reloadReview(String reviewId) {
		// TODO Auto-generated method stub
		return null;
	}

	//리뷰 수정하기
	@Override
	public int updateReview(Review review, List<MultipartFile> reviewImgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
