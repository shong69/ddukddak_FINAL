package com.ddukddak.ecommerce.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.ddukddak.ecommerce.model.dto.BigCategory;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.mapper.eCommerceMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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
	public List<Product> selectProductList(int categoryNo) {
		return mapper.selectProductList(categoryNo);
	}

	// 대분류 카테고리 선택
	@Override
	public List<BigCategory> selectCategory() {
		return mapper.selectCategory();
	}
	
	

}
