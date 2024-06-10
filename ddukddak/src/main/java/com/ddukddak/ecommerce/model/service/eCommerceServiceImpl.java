package com.ddukddak.ecommerce.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.DetailProduct;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductImg;
import com.ddukddak.ecommerce.model.dto.ProductOption;
import com.ddukddak.ecommerce.model.dto.eCommercePagination;
import com.ddukddak.ecommerce.model.mapper.eCommerceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class eCommerceServiceImpl implements eCommerceService{
	
	private final eCommerceMapper mapper;

	// 쇼핑몰 메인페이지 오늘의 상품 고르기
	@Override
	public List<Product> selectProduct() {
		
		List<Product> list = mapper.selectProduct();
        
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
	public List<Product> selectBestProduct() {
		
		List<Product> list = mapper.selectBestProduct();
		
		List<Product> bestList = new ArrayList<Product>();
		
		for(int i = 0; i < 4; i++) {
			bestList.add(list.get(i));
        }
		
		return bestList;
	}

	// 카테고리별 상품목록 띄우기
	@Override
	public Map<String, Object> selectProductList(int smallcategoryNo, int cp) {
		
		log.debug("cp : " + cp);
		//1. 전체 게시글 수 조회
		int productCount = mapper.selectProductListCount(smallcategoryNo);
		
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
	public DetailProduct selectOneProduct(int productNo) {
		
		DetailProduct detailProduct = mapper.selectOneProduct(productNo);
		
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
	public List<Product> selectRecProduct(int productNo, int smallcategoryNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("productNo", productNo);
		map.put("smallcategoryNo", smallcategoryNo);
		
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
	
	

}
