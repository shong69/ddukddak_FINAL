package com.ddukddak.partner.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.board.model.dto.BoardImg;
import com.ddukddak.board.model.exception.BoardInsertException;
import com.ddukddak.common.util.Utility;
import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductImg;
import com.ddukddak.ecommerce.model.dto.eCommercePagination;
import com.ddukddak.partner.model.dto.ProductPagination;
import com.ddukddak.partner.model.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor=Exception.class)
@PropertySource("classpath:/config.properties")
@Slf4j
public class ProductServiceImpl implements ProductService{
	
	private final ProductMapper mapper;
	
	@Value("${my.ecommerce.web-path}")
	private String webPath;
	
	@Value("${my.ecommerce.folder-path}")
	private String folderPath;

	// 재고등록 재고상품 조회
	@Override
	public Map<String, Object> selectCreateList(int mainSort, int sort, int cp) {
		
		if(mainSort == 0) {
			//1. 전체 재고상품개수 조회
			int productCount = mapper.selectCreateListCount();
			
			//2. pagination 객체 생성하기
			ProductPagination pagination = new ProductPagination(cp, productCount);
			//3. 페이지 목록 조회
			int limit = pagination.getLimit(); //제한된 크기
			int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
			
			RowBounds rowBounds = new RowBounds(offset, limit);
			
			List<Product> createList = mapper.selectCreateList(rowBounds);
			
			Map<String, Object> map = new HashMap<>();
			map.put("pagination", pagination);
			map.put("createList", createList);
			
			return map;
		} else {
			if(sort == 0) {
				//1. 전체 재고상품개수 조회
				int productCount = mapper.selectCreateListCountMainSort(mainSort);
				
				//2. pagination 객체 생성하기
				ProductPagination pagination = new ProductPagination(cp, productCount);
				//3. 페이지 목록 조회
				int limit = pagination.getLimit(); //제한된 크기
				int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
				
				RowBounds rowBounds = new RowBounds(offset, limit);
				
				List<Product> createList = mapper.selectCreateListMainSort(mainSort, rowBounds);
				
				Map<String, Object> map = new HashMap<>();
				map.put("pagination", pagination);
				map.put("createList", createList);
				
				return map;
			} else {
				//1. 전체 재고상품개수 조회
				int productCount = mapper.selectCreateListCountSort(sort);
				
				//2. pagination 객체 생성하기
				ProductPagination pagination = new ProductPagination(cp, productCount);
				//3. 페이지 목록 조회
				int limit = pagination.getLimit(); //제한된 크기
				int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
				
				RowBounds rowBounds = new RowBounds(offset, limit);
				
				List<Product> createList = mapper.selectCreateListSort(sort, rowBounds);
				
				Map<String, Object> map = new HashMap<>();
				map.put("pagination", pagination);
				map.put("createList", createList);
				
				return map;	
			}
		}
		
		
	}

	// 재고상품 판매등록
	@Override
	public int sellApplyProduct(List<Object> selectedValues) {
		int result = 0;
		
		for(Object value : selectedValues) {
			result += mapper.sellApplyProduct(value);
		}
		
		return result;
	}

	// 대분류에 따른 소분류 조회
	@Override
	public List<Category> selectSmallCategory(int selectedCategory) {
		return mapper.selectSmallCategory(selectedCategory);
	}

	// 상품 재고등록
	@Override
	public int registProduct(Map<String, Object> map) {
		return mapper.registProduct(map);
	}

	// 상품 재고등록 이미지 등록
	@Override
	public int insertImg(int smallCategory, List<MultipartFile> imgList) throws IllegalStateException, IOException {
		
		List<ProductImg> uploadList = new ArrayList<>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = 0;
		
		if(!imgList.isEmpty()) {
			
			for(int i = 0; i < imgList.size(); i++) {
				
				// 원본명
				String originalName = imgList.get(i).getOriginalFilename();
				
				// 변경명
				String rename = Utility.fileRename(originalName);
				
				map.put("uploadImgOgName", originalName);
				map.put("uploadImgRename", rename);
				map.put("uploadImgPath", webPath);
				map.put("category", smallCategory);
				map.put("uploadImgOrder", i);
				
				result += mapper.insertImg(map);
				
				ProductImg img = ProductImg.builder()
						   .uploadImgOgName(originalName)
						   .uploadImgRename(rename)
						   .uploadImgPath(webPath)
						   .uploadImgOrder(i)
						   .uploadFile(imgList.get(i))
						   .build();
			
				uploadList.add(img);
				
			}
			
		} else {
			return 0;
		}
		
		// 폴더에 이미지저장
		if(result == imgList.size()) {
			for(ProductImg img : uploadList) {
				img.getUploadFile().transferTo( new File(folderPath + img.getUploadImgRename()) );
			}
			return result;
		} else {
			throw new BoardInsertException("이미지가 정상 삽입되지 않음");
		}
	}

	// 상품 재고등록 옵션등록
	@Override
	public int insertOpion(String item1, List<String> list, List<String> list2) {
		int result = 0 ;
		
		for(int i = 0; i < list.size(); i ++) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("optionName", item1);
			map.put("optionValue", list.get(i));
			map.put("productCount", Integer.parseInt(list2.get(i)));
			
			result += mapper.insertOption(map);
		}
		
		return result;
	}

}
