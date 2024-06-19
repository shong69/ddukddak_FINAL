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
import com.ddukddak.ecommerce.model.dto.ProductOption;
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
	
		// 대분류 소분류 모두 선택 안했을 때
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
			
		// 대분류만 선택했을 때
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
				
			// 대분류 소분류 모두 선택했을 때
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
	
	// 재고상품 삭제
	@Override
	public int delProduct(int productNo) {
		
		int result = mapper.delProductOption(productNo);
		result += mapper.delProductImg(productNo);
		result += mapper.delProduct(productNo);
		
		return result;
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
				if(!originalName.equals("")) {
					log.info("name : " +originalName);
					
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
				
			}
			
		} else {
			return 0;
		}
		
		// 폴더에 이미지저장
		for(ProductImg img : uploadList) {
			img.getUploadFile().transferTo( new File(folderPath + img.getUploadImgRename()) );
		}
		return result;
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

	// 판매관리 상품조회
	@Override
	public Map<String, Object> selectApplyList(int mainSort, int sort, String status, int cp) {
		
		// 대분류 소분류 상태 모두 선택 안했을 때
		if(mainSort == 0) {
			if(status.equals("A")) {
				//1. 전체 재고상품개수 조회
				int productCount = mapper.selectApplyListCount();
				
				//2. pagination 객체 생성하기
				ProductPagination pagination = new ProductPagination(cp, productCount);
				//3. 페이지 목록 조회
				int limit = pagination.getLimit(); //제한된 크기
				int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
				
				RowBounds rowBounds = new RowBounds(offset, limit);
				
				List<Product> applyList = mapper.selectApplyList(rowBounds);
				
				Map<String, Object> map = new HashMap<>();
				map.put("pagination", pagination);
				map.put("applyList", applyList);
				
				return map;
				
			// 상태만 선택했을 때
			} else {
				//1. 전체 재고상품개수 조회
				int productCount = mapper.selectApplyListCountStatus(status);
				
				//2. pagination 객체 생성하기
				ProductPagination pagination = new ProductPagination(cp, productCount);
				//3. 페이지 목록 조회
				int limit = pagination.getLimit(); //제한된 크기
				int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
				
				RowBounds rowBounds = new RowBounds(offset, limit);
				
				List<Product> applyList = mapper.selectApplyListStatus(status, rowBounds);
				
				Map<String, Object> map = new HashMap<>();
				map.put("pagination", pagination);
				map.put("applyList", applyList);
				
				return map;
			}
			
		// 대분류만 선택했을 때
		} else {
			if(sort == 0) {
				if(status.equals("A")) {	
					//1. 전체 재고상품개수 조회
					int productCount = mapper.selectApplyListCountMainSort(mainSort);
					
					//2. pagination 객체 생성하기
					ProductPagination pagination = new ProductPagination(cp, productCount);
					//3. 페이지 목록 조회
					int limit = pagination.getLimit(); //제한된 크기
					int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
					
					RowBounds rowBounds = new RowBounds(offset, limit);
					
					List<Product> applyList = mapper.selectApplyListMainSort(mainSort, rowBounds);
					
					Map<String, Object> map = new HashMap<>();
					map.put("pagination", pagination);
					map.put("applyList", applyList);
					
					return map;	
					
				// 대분류와 상태 선택했을 때
				} else {
					Map<String, Object> newMap = new HashMap<String, Object>();
					
					newMap.put("mainSort", mainSort);
					newMap.put("status", status);
					
					//1. 전체 재고상품개수 조회
					int productCount = mapper.selectApplyListCountMainSortStatus(newMap);
					
					//2. pagination 객체 생성하기
					ProductPagination pagination = new ProductPagination(cp, productCount);
					//3. 페이지 목록 조회
					int limit = pagination.getLimit(); //제한된 크기
					int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
					
					RowBounds rowBounds = new RowBounds(offset, limit);
					
					List<Product> applyList = mapper.selectApplyListMainSortStatus(newMap, rowBounds);
					
					Map<String, Object> map = new HashMap<>();
					map.put("pagination", pagination);
					map.put("applyList", applyList);
					
					return map;
				}
				
			// 대분류 소분류 모두 선택했을 때
			} else {
				if(status.equals("A")) {
					//1. 전체 재고상품개수 조회
					int productCount = mapper.selectApplyListCountSort(sort);
					
					//2. pagination 객체 생성하기
					ProductPagination pagination = new ProductPagination(cp, productCount);
					//3. 페이지 목록 조회
					int limit = pagination.getLimit(); //제한된 크기
					int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
					
					RowBounds rowBounds = new RowBounds(offset, limit);
					
					List<Product> applyList = mapper.selectApplyListSort(sort, rowBounds);
					
					Map<String, Object> map = new HashMap<>();
					map.put("pagination", pagination);
					map.put("applyList", applyList);
					
					return map;		
					
				// 대분류 소분류 상태 모두 선택했을 때
				} else {
					Map<String, Object> newMap = new HashMap<String, Object>();
					
					newMap.put("sort", sort);
					newMap.put("status", status);
					
					//1. 전체 재고상품개수 조회
					int productCount = mapper.selectApplyListCountSortStatus(newMap);
					
					//2. pagination 객체 생성하기
					ProductPagination pagination = new ProductPagination(cp, productCount);
					//3. 페이지 목록 조회
					int limit = pagination.getLimit(); //제한된 크기
					int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
					
					RowBounds rowBounds = new RowBounds(offset, limit);
					
					List<Product> applyList = mapper.selectApplyListSortStatus(newMap, rowBounds);
					
					Map<String, Object> map = new HashMap<>();
					map.put("pagination", pagination);
					map.put("applyList", applyList);
					
					return map;
				}
			}
		}
	}

	// 판매상태 변경
	@Override
	public int changeStatus(Map<String, Object> map) {
		List<Object> list = (List<Object>) map.get("obj");
		String getStatus = (String) map.get("status");
		
		String status = "";
		
		if(getStatus.equals("판매중")) {
			status = "N";
		} else if(getStatus.equals("판매중지")) {
			status = "S";
		} else {
			status = "Y";
		}
		
		int result = 0;
		
		for(Object productNo : list) {
			Map<String, Object> newMap = new HashMap<String, Object>();
			
			newMap.put("productNo", productNo);
			newMap.put("status", status);
			
			result += mapper.changeStatus(newMap);
		}
		return result;
	}
	
	// 판매등록 상품 조회
	@Override
	public Product selectOne(int productNo) {
		return mapper.selectOne(productNo);

	}

	// 판매등록 상품 이미지 조회
	@Override
	public List<ProductImg> selectImg(int productNo) {
		return mapper.selectImg(productNo);
	}

	// 판매등록 상품 옵션명 조회
	@Override
	public List<ProductOption> seletOptionName(int productNo) {
		return mapper.selectOptionName(productNo);
	}

	// 판매등록 상품 옵션 조회
	@Override
	public List<ProductOption> selectOpion(int productNo) {
		return mapper.selectOption(productNo);
	}

	// 상품 판매등록
	@Override
	public int updateRegistProduct(Map<String, Object> map) {
		return mapper.updateRegistProduct(map);
	}

	// 이미지 판매등록
	@Override
	public int updateInsertImg(String proudctNo, String smallCategory, List<MultipartFile> imgList) throws IllegalStateException, IOException {
		List<ProductImg> uploadList = new ArrayList<>();
		
		log.info("imgList" + imgList);
	
		// 상세사진 업로드가 없을 때
		if(imgList.isEmpty()) {
			return 1;
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			
			int result = 0;
			
			for(int i = 1; i < imgList.size(); i++) {
				
				// 원본명
				String originalName = imgList.get(i).getOriginalFilename();
				if(!originalName.equals("")) {
					log.info("name : " +originalName);
					
					// 변경명
					String rename = Utility.fileRename(originalName);
					
					map.put("uploadImgOgName", originalName);
					map.put("uploadImgRename", rename);
					map.put("uploadImgPath", webPath);
					map.put("category", smallCategory);
					map.put("productNo", Integer.parseInt(proudctNo));
					map.put("uploadImgOrder", i);
					
					result += mapper.insertImg2(map);
					
					ProductImg img = ProductImg.builder()
							.uploadImgOgName(originalName)
							.uploadImgRename(rename)
							.uploadImgPath(webPath)
							.uploadImgOrder(i)
							.uploadFile(imgList.get(i))
							.build();
					
					uploadList.add(img);
					
				}
				
			}
			// 폴더에 이미지저장
			for(ProductImg img : uploadList) {
				img.getUploadFile().transferTo( new File(folderPath + img.getUploadImgRename()) );
			}
			return result;
			
		}
		
	}

	// 대표사진 업데이트
	@Override
	public int updateThumbnailImg(MultipartFile thumbnailImg, String productNo, String smallCategory) throws IllegalStateException, IOException {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		ProductImg img = new ProductImg();
		
		if(thumbnailImg == null) {
			return 1;
		} else {
			//원본명
			String originalName = thumbnailImg.getOriginalFilename();
			if(!originalName.equals("")) {
				log.info("name : " +originalName);
				
				// 변경명
				String rename = Utility.fileRename(originalName);
				
				log.info("mainRename : " + rename);
				
				map.put("uploadImgOgName", originalName);
				map.put("uploadImgRename", rename);
				map.put("uploadImgPath", webPath);
				map.put("productNo", productNo);
				map.put("category", smallCategory);
				map.put("uploadImgOrder", 0);
				
				log.info("map : " + map);
				
				result += mapper.updateThumbnail(map);
				
				img = ProductImg.builder()
						.uploadImgOgName(originalName)
						.uploadImgRename(rename)
						.uploadImgPath(webPath)
						.uploadImgOrder(0)
						.uploadFile(thumbnailImg)
						.build();
				
				// 폴더에 이미지저장
				img.getUploadFile().transferTo( new File(folderPath + img.getUploadImgRename()) );
				
				return result;
			}
			
		}
		return result;
	
	}

	// 상세사진 삭제
	@Override
	public int delImg(int imgNo) {
		return mapper.delProductImg2(imgNo);
	}

	// 옵션 비우기
	@Override
	public int delOption(String productNo) {
		return mapper.delOption(productNo);
	}

	// 새 옵션 추가
	@Override
	public int insertOpion2(String item1, List<String> list, List<String> list2, String productNo) {
		int result = 0 ;
		
		for(int i = 0; i < list.size(); i ++) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("optionName", item1);
			map.put("optionValue", list.get(i));
			map.put("productCount", Integer.parseInt(list2.get(i)));
			map.put("productNo", productNo);
			result += mapper.insertOption2(map);
		}
		
		return result;
	}

	// 수정상품 등록
	@Override
	public int modifyRegistProduct(Map<String, Object> map) {
		return mapper.modifyRegistProduct(map);
	}

	@Override
	public Map<String, Object> selectReciptList(int partnerNo, int mainSort, int sort, String status, int cp) {
		
		// 대분류 소분류 상태 모두 선택 안했을 때
		if(mainSort == 0) {
			if(status.equals("A")) {
				//1. 전체 재고상품개수 조회
				int productCount = mapper.selectApplyListCount();
				
				//2. pagination 객체 생성하기
				ProductPagination pagination = new ProductPagination(cp, productCount);
				//3. 페이지 목록 조회
				int limit = pagination.getLimit(); //제한된 크기
				int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
				
				RowBounds rowBounds = new RowBounds(offset, limit);
				
				List<Product> applyList = mapper.selectApplyList(rowBounds);
				
				Map<String, Object> map = new HashMap<>();
				map.put("pagination", pagination);
				map.put("applyList", applyList);
				
				return map;
				
			// 상태만 선택했을 때
			} else {
				//1. 전체 재고상품개수 조회
				int productCount = mapper.selectApplyListCountStatus(status);
				
				//2. pagination 객체 생성하기
				ProductPagination pagination = new ProductPagination(cp, productCount);
				//3. 페이지 목록 조회
				int limit = pagination.getLimit(); //제한된 크기
				int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
				
				RowBounds rowBounds = new RowBounds(offset, limit);
				
				List<Product> applyList = mapper.selectApplyListStatus(status, rowBounds);
				
				Map<String, Object> map = new HashMap<>();
				map.put("pagination", pagination);
				map.put("applyList", applyList);
				
				return map;
			}
			
		// 대분류만 선택했을 때
		} else {
			if(sort == 0) {
				if(status.equals("A")) {	
					//1. 전체 재고상품개수 조회
					int productCount = mapper.selectApplyListCountMainSort(mainSort);
					
					//2. pagination 객체 생성하기
					ProductPagination pagination = new ProductPagination(cp, productCount);
					//3. 페이지 목록 조회
					int limit = pagination.getLimit(); //제한된 크기
					int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
					
					RowBounds rowBounds = new RowBounds(offset, limit);
					
					List<Product> applyList = mapper.selectApplyListMainSort(mainSort, rowBounds);
					
					Map<String, Object> map = new HashMap<>();
					map.put("pagination", pagination);
					map.put("applyList", applyList);
					
					return map;	
					
				// 대분류와 상태 선택했을 때
				} else {
					Map<String, Object> newMap = new HashMap<String, Object>();
					
					newMap.put("mainSort", mainSort);
					newMap.put("status", status);
					
					//1. 전체 재고상품개수 조회
					int productCount = mapper.selectApplyListCountMainSortStatus(newMap);
					
					//2. pagination 객체 생성하기
					ProductPagination pagination = new ProductPagination(cp, productCount);
					//3. 페이지 목록 조회
					int limit = pagination.getLimit(); //제한된 크기
					int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
					
					RowBounds rowBounds = new RowBounds(offset, limit);
					
					List<Product> applyList = mapper.selectApplyListMainSortStatus(newMap, rowBounds);
					
					Map<String, Object> map = new HashMap<>();
					map.put("pagination", pagination);
					map.put("applyList", applyList);
					
					return map;
				}
				
			// 대분류 소분류 모두 선택했을 때
			} else {
				if(status.equals("A")) {
					//1. 전체 재고상품개수 조회
					int productCount = mapper.selectApplyListCountSort(sort);
					
					//2. pagination 객체 생성하기
					ProductPagination pagination = new ProductPagination(cp, productCount);
					//3. 페이지 목록 조회
					int limit = pagination.getLimit(); //제한된 크기
					int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
					
					RowBounds rowBounds = new RowBounds(offset, limit);
					
					List<Product> applyList = mapper.selectApplyListSort(sort, rowBounds);
					
					Map<String, Object> map = new HashMap<>();
					map.put("pagination", pagination);
					map.put("applyList", applyList);
					
					return map;		
					
				// 대분류 소분류 상태 모두 선택했을 때
				} else {
					Map<String, Object> newMap = new HashMap<String, Object>();
					
					newMap.put("sort", sort);
					newMap.put("status", status);
					
					//1. 전체 재고상품개수 조회
					int productCount = mapper.selectApplyListCountSortStatus(newMap);
					
					//2. pagination 객체 생성하기
					ProductPagination pagination = new ProductPagination(cp, productCount);
					//3. 페이지 목록 조회
					int limit = pagination.getLimit(); //제한된 크기
					int offset = (cp-1) * limit; //건너뛰기 :  데이터를 가져오는 시작점에서 얼마나 떨어진 데이터인지를 의미
					
					RowBounds rowBounds = new RowBounds(offset, limit);
					
					List<Product> applyList = mapper.selectApplyListSortStatus(newMap, rowBounds);
					
					Map<String, Object> map = new HashMap<>();
					map.put("pagination", pagination);
					map.put("applyList", applyList);
					
					return map;
				}
			}
		}
	}



}
