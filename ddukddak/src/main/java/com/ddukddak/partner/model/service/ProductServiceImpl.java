package com.ddukddak.partner.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<Product> selectCreateList() {
		return mapper.selectCreateList();
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
