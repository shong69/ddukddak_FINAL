package com.ddukddak.ecommerce.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.DetailProduct;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductImg;
import com.ddukddak.ecommerce.model.dto.ProductOption;
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
	public List<Product> selectProductList(int smallcategoryNo) {
		return mapper.selectProductList(smallcategoryNo);
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
	public List<Product> selectRecProduct(int smallcategoryNo) {
		List<Product> list = mapper.recProduct(smallcategoryNo);
        
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
