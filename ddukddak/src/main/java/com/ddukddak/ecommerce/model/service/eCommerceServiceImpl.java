package com.ddukddak.ecommerce.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.common.util.Utility;
import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.DetailProduct;
import com.ddukddak.ecommerce.model.dto.Orders;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductImg;
import com.ddukddak.ecommerce.model.dto.ProductOption;
import com.ddukddak.ecommerce.model.dto.QNA;
import com.ddukddak.ecommerce.model.dto.Review;
import com.ddukddak.ecommerce.model.dto.ReviewImg;
import com.ddukddak.ecommerce.model.dto.eCommercePagination;
import com.ddukddak.ecommerce.model.mapper.eCommerceMapper;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.myPage.model.dto.Order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class eCommerceServiceImpl implements eCommerceService{
	
	private final eCommerceMapper mapper;
	
	@Value("${my.review.web-path}")
	private String webPath;
	
	@Value("${my.review.folder-path}")
	private String folderPath;
	

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


	
	/** 멤버 찾기(주소 이슈 때문에)
	 *
	 */
	@Override
	public Member selectMember(int memberNo) {
		// 
		return mapper.selectMember(memberNo);
	}

	
	
	// 주문 생성
	@Override
	public int createOrder(Orders order) {
		
		return mapper.createOrder(order);
	}


	

	



	//리뷰 목록 조회하기
	@Override
	public List<Review> selectReviewList(int productNo) {
		List<Review> list = mapper.selectReviewList(productNo);

		//리뷰 넘버로 이미지 조회해서 가져오기
		for(Review review : list) {
			int reviewNo = review.getReviewNo();
			List<String> imgList = mapper.selectReviewImgs(reviewNo);
//			log.info("이미지리스트 : {}",imgList);
			review.setImgList(imgList);
			}
		
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

	//리뷰 등록하기 + 리뷰 넘버 리턴하기
	@Override
	public int postReview(Map<String, Object> map) {
		//주문 테이블에서 memberNo와 productNo에 해당하는 orderItemNo 찾아오기(리뷰를 써야 하는 주문상품)
		int result = mapper.postReview(map);
		int reviewNo =0;
		if(result != 0) {
			reviewNo = mapper.getReviewNo(map);
		}

		return reviewNo;
	}

	//리뷰 사진 등록하기  
	@Override
	public int insertImgs(int reviewNo, List<MultipartFile> reviewImgs) throws IllegalStateException, IOException {
		List<ReviewImg> uploadList = new ArrayList<>();
		int result = 0;
		
		if(!reviewImgs.isEmpty()) {
			for(int i = 0; i<reviewImgs.size();i++) {
				
				String originalName =reviewImgs.get(i).getOriginalFilename();
				if(!originalName.equals("")) {
					log.info("이름{}",originalName);
					Map<String, Object> map = new HashMap<>();
					String rename = Utility.fileRename(originalName);
					map.put("reviewNo", reviewNo);
					map.put("uploadImgOrder", i);
					map.put("uploadImgOgName", originalName);
					map.put("uploadImgRename", rename);
					map.put("uploadImgPath", webPath);
					
					result += mapper.insertImgs(map);
					log.info("사진 삽입 결과{}",result);
					ReviewImg img = ReviewImg.builder()
							.reviewNo(reviewNo)
							.uploadImgOrder(i)
							.uploadImgPath(webPath)
							.uploadImgOgName(originalName)
							.uploadImgRename(rename)
							.uploadFile(reviewImgs.get(i))
							.build();
					uploadList.add(img);				
					}
			}
			for(ReviewImg img : uploadList) {
				img.getUploadFile().transferTo(new File(folderPath + img.getUploadImgRename()));
			}
			return result;
		}else { //이미지 안보낸 경우
			return 1;
		}

	}
	
	//리뷰 삭제하기
	@Override
	public int delReview(int reviewNo) {
		
		return mapper.delReview(reviewNo);
	}

	//수정할 리뷰 불러오기
	@Override
	public Review reloadReview(int reviewNo) {

		//리뷰 사진 불러오기
		List<String> imgList = mapper.selectReviewImgs(reviewNo);
		Review review = mapper.reloadReview(reviewNo);
		if(imgList != null) {
			review.setImgList(imgList);
		}
		log.info("수정리뷰{}",review);
		return review;
	}

	//리뷰 이미지 삭제
	@Override
	public int delImg(Map<String,String> map) {
		int result = mapper.delImg(map);
		return result;
	}

	private static final Logger log = LoggerFactory.getLogger(eCommerceServiceImpl.class);

    // 리뷰 수정하기
    @Override
    public int updateReview(Review review, List<MultipartFile> reviewImgs) throws IllegalStateException, IOException {
        log.info("리뷰 수정 시작 - 리뷰 번호: {}", review.getReviewNo());
        
        // 리뷰 + 사진 등록
        int result = mapper.updateReview(review);
        int result1 = 0;
        
        log.info("리뷰 업데이트 결과: {}", result);

        if (result > 0) {
            List<ReviewImg> imgList = new ArrayList<>();
            if (reviewImgs != null || !reviewImgs.isEmpty()) {
                log.info("업로드할 이미지 수: {}", reviewImgs.size()-1);
                if(reviewImgs.size()>1) {
                    Map<String, Object> map = new HashMap<>();

                    for (int i = 1; i < reviewImgs.size(); i++) {
                    	
                        MultipartFile file = reviewImgs.get(i);
                        log.info("이미지 내놔: {}", file);

                        // 파일의 원본 이름을 가져옵니다.
                        String originalName = file.getOriginalFilename();
                        log.info("Original file name: {}", originalName);

                        log.info("원본 파일 이름: {}", originalName);

                        if (originalName != null && !originalName.isEmpty()) {
                            String rename = Utility.fileRename(originalName);
                            log.info("변경된 파일 이름: {}", rename);
                            
                            map.put("reviewNo", review.getReviewNo());
                            map.put("uploadImgOgName", originalName);
                            map.put("uploadImgRename", rename);
                            map.put("uploadImgPath", webPath);
                            map.put("uploadImgOrder", i);
                            
                            try {
                                int insertResult = mapper.insertImgs(map);
                                result1 += insertResult;
                                log.info("이미지 삽입 결과: {}", insertResult);

                                ReviewImg img = ReviewImg.builder()
                                        .reviewNo(review.getReviewNo())
                                        .uploadImgOgName(originalName)
                                        .uploadImgRename(rename)
                                        .uploadImgOrder(i)
                                        .uploadImgPath(webPath)
                                        .uploadFile(reviewImgs.get(i))
                                        .build();
                                imgList.add(img);
                            } catch (Exception e) {
                                log.error("이미지 삽입 중 오류 발생 - 리뷰 번호: {}, 이미지: {}", review.getReviewNo(), originalName, e);
                            }
                        }
                    }

                    for (ReviewImg img : imgList) {
                        File file = new File(folderPath + img.getUploadImgRename());
                        try {
                            img.getUploadFile().transferTo(file);
                            log.info("파일 저장 성공 - 경로: {}", file.getAbsolutePath());
                        } catch (IOException e) {
                            log.error("파일 저장 실패 - 경로: {}", file.getAbsolutePath(), e);
                        }
                    }
                }
                else {
                    log.info("업로드할 이미지가 없음");
                    return result;
            } 
            }
        }
		return result1 + result;
    }

	//리뷰 개수 리턴
	@Override
	public int reviewCount(int productNo) {
		return mapper.reviewCount(productNo);
	}


	//리뷰 평점 리턴
	@Override
	public double avgScore(int productNo) {
		log.info("평점 결과:{}",mapper.avgScore(productNo));
		Double avgScore = mapper.avgScore(productNo);

		return avgScore!= null?avgScore:0.0;
	}
	
	
	
	
	
	// qna 입력
	@Override
	public int insertQna(Map<String, Object> obj) {
		return mapper.insertQna(obj);
}
	// 모든 qna 보기
	@Override
	public List<QNA> selectQna() {
		return mapper.selectQna();
	}

	// 내 qna 보기
	@Override
	public List<QNA> myQna(int memberNo) {
		return mapper.myQna(memberNo);
	}


	// 사전 등록 DB 검증 
	@Override
	public Orders prepareOrder(String merchantUid) {
		
		return mapper.prepareOrder(merchantUid);
	}
	
	// 사전 완료 건 업데이트
	@Override
	public int readyUpdate(String merchantUid) {

		return mapper.readyUpdate(merchantUid);
	}

	// 사후 검증 완료건 최종처리
	@Override
	public int paidUpdate(String merchantUid) {

		return mapper.paidUpdate(merchantUid);
	}


	// 사용자 결제 취소 업데이트
	@Override
	public int cancelUpdate(Map<String, String> map) {

		return mapper.cancelUpdate(map);

	}

	
	// ORDERS 테이블 조회 용도
	@Override
	public Orders findOrder(String merchantUid) {
		
		return mapper.findOrder(merchantUid);
	}

	// ORDER_DETAIL 삽입
	@Override
	public int insertOrderDetail(int orderNo, int memberNo, int cartId, int productNo, int productCount,
			int productPrice) {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("orderNo", orderNo);
		map.put("productNo", productNo);
		map.put("orderQuantity", productCount);
		map.put("orderPrice", productPrice);
		map.put("orderStatus", "구매확정");
		map.put("cartId", cartId);
		
		return mapper.insertOrderDetail(map);
	}
	
	// 현재 포인트 조회
	@Override
	public int currentPoint(int memberNo) {
		
		return mapper.currentPoint(memberNo);
	}

	// 포인트 적립 업데이트
	@Override
	public int savePoint(int memberNo, int totalPoint) {
		
		Map<String, Integer> map = new HashMap<>();
		
		map.put("memberNo", memberNo);
		map.put("totalPoint", totalPoint);
		
		
		return mapper.savePoint(map);
	}

	
	// 주문상세옵션 테이블을 삽입할 오더 디테일의 아이템 번호 구하기
	@Override
	public int getOrderItemNo(int orderNo, int productNo, int cartId) {
		
		Map<String, Integer> map = new HashMap<>();
		
		map.put("orderNo", orderNo);
		map.put("productNo", productNo);
		map.put("cartId", cartId);
		
		return mapper.getOrderItemNo(map);
	}

	
	// 주문상세옵션 테이블 삽입
	@Override
	public int insertOrderDetailOption(int orderItemNo, Integer optionNo) {
		
		Map<String, Integer> map = new HashMap<>();
		
		map.put("orderItemNo", orderItemNo);
		map.put("optionNo", optionNo);
		
		return mapper.insertOrderDetailOption(map);

	}

	// 결제완료용 추천상품 6개 가져오기
	@Override
	public List<Product> selectProduct6(int memberNo) {
		List<Product> list = mapper.selectProduct(memberNo);
		        
		        List<Product> randomList = new ArrayList<Product>();
		        
		        List<Integer> saveNo = new ArrayList<Integer>();
		        
		        for(int i = 0; i < 6; i++) {
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


	

}
