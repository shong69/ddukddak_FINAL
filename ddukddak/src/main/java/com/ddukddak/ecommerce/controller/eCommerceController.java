package com.ddukddak.ecommerce.controller;


import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.DetailProduct;
import com.ddukddak.ecommerce.model.dto.Orders;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductOption;
import com.ddukddak.ecommerce.model.dto.QNA;
import com.ddukddak.ecommerce.model.dto.Review;
import com.ddukddak.ecommerce.model.service.eCommerceService;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.myPage.model.dto.CartItem;
import com.ddukddak.myPage.model.dto.Order;
import com.ddukddak.myPage.model.service.CartAndWishListService;
import com.ddukddak.payment.model.dto.PaymentDTO;
import com.ddukddak.payment.model.service.PaymentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("eCommerce")
@RequiredArgsConstructor
@SessionAttributes("loginMember")
@Slf4j
public class eCommerceController {
	
	
	// 쇼핑몰 메인페이지
	private final eCommerceService service;
	private final CartAndWishListService cartService;
	private final PaymentService paymentService;
	
	@GetMapping("main")
	public String eCommerceMain(@RequestParam(value="cp", required=false, defaultValue="1") int cp,
								@RequestParam(value="query", required=false) String query,
								HttpServletRequest req,
								Model model) {
		
		HttpSession session = req.getSession();
		
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		int memberNo = 0;
		
		if(loginMember != null) {
			memberNo = loginMember.getMemberNo();
		}
		
		// 대분류 카테고리 선택
		List<Category> categoryList = service.selectCategory();
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("loginMember", loginMember);
		
		if(query != null) {
			Map<String, Object> map = service.searchList(memberNo, query, cp);
			
			int bigcategoryNo = 1;
			int smallcategoryNo = 1;
			
			model.addAttribute("selectProductList", map.get("productList"));
			model.addAttribute("pagination", map.get("pagination"));
			model.addAttribute("bigCategoryNo", bigcategoryNo);
			model.addAttribute("smallCategoryNo", smallcategoryNo);
			model.addAttribute("query", query);
			
			return "eCommerce/eCommerceList";
		} else {
			List<Product> list = service.selectProduct(memberNo);
			List<Product> bestList = service.selectBestProduct(memberNo);
			
			
			model.addAttribute("selectProductList", list);
			model.addAttribute("selectBestProductList", bestList);
			
			
			return "eCommerce/eCommerceMain";		
		}
		

	}
	
	@PostMapping("selectBestProduct")
	@ResponseBody
	public List<Product> selectBestProduct(Model model,
											@RequestBody int categoryNo) {
		
		List<Product> bestProduct = service.BestProduct(categoryNo);
		
		model.asMap().remove("selectBestProductList");
		model.addAttribute("selectBestProductList", bestProduct);
		
		return bestProduct;
		
	}
	
	@GetMapping("list/{bigcategoryNo:[0-9]+}/{smallcategoryNo:[0-9]+}")
	public String eCommerceList(@PathVariable("bigcategoryNo") int bigcategoryNo,
								@PathVariable("smallcategoryNo") int smallcategoryNo,
								@RequestParam(value="cp", required=false, defaultValue="1") int cp,
								@RequestParam(value="query", required=false) String query,
								@RequestParam(value="sort", required=false, defaultValue="1") int sort,
								@RequestParam(value="minPrice", required=false, defaultValue="0") int minPrice,
								@RequestParam(value="maxPrice", required=false, defaultValue="9999999") int maxPrice,
								HttpServletRequest req,
								Model model) {
		
		Map<String, Object> map = null;
		
		HttpSession session = req.getSession();
		
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		int memberNo = 0;
		
		if(loginMember != null) {
			memberNo = loginMember.getMemberNo();
		}	
		
		if(query == null) { //검색 X(그냥 목록 조회)

			map = service.selectProductList(memberNo, smallcategoryNo, cp, sort, minPrice, maxPrice);
			
			log.info("map : " + map);
			log.info("minPrice : " + minPrice);
			log.info("maxPrice : " + maxPrice);
			
		}else { //검색 O
			
			map = service.searchList(memberNo, query, cp, sort, minPrice, maxPrice);
			
		}
		
		// 대분류 카테고리 선택
		List<Category> categoryList = service.selectCategory();
		
		// 소분류 카테고리 선택
		List<Category> smallCategoryList = service.selectSmallCategory(bigcategoryNo);
		
		String bigCategoryName = service.selectBigCategory(bigcategoryNo);

		model.addAttribute("selectProductList", map.get("productList"));
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("smallCategoryList", smallCategoryList);
		model.addAttribute("bigCategoryName", bigCategoryName);
		model.addAttribute("bigCategoryNo", bigcategoryNo);
		model.addAttribute("smallCategoryNo", smallcategoryNo);
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);

		model.addAttribute("query", query);
		
		

		model.addAttribute("loginMember", loginMember);
		
		return "eCommerce/eCommerceList";
	}
	
	
	@GetMapping("list/{bigcategoryNo:[0-9]+}/{smallcategoryNo:[0-9]+}/{productNo:[0-9]+}/detail")
	public String eCommerceDetail(@PathVariable("bigcategoryNo") int bigcategoryNo,
								@PathVariable("smallcategoryNo") int smallcategoryNo,
								@PathVariable("productNo") int productNo,
								@RequestParam(value="cp", required=false, defaultValue="1") int cp,
								@RequestParam(value="query", required=false) String query,
								HttpServletRequest req,
									Model model) {
		


		HttpSession session = req.getSession();
		
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		log.info("loginMember" + loginMember);
		
		int memberNo = 0;
		
		if(loginMember != null) {
			memberNo = loginMember.getMemberNo();
		}
		model.addAttribute("loginMember", loginMember);
		
		DetailProduct productInfo = service.selectOneProduct(memberNo, productNo);
		
		// 대분류 카테고리 선택
		List<Category> categoryList = service.selectCategory();
				
		// 소분류 카테고리 선택
		List<Category> smallCategoryList = service.selectSmallCategory(bigcategoryNo);
		
		String bigCategoryName = service.selectBigCategory(bigcategoryNo);
		
		// 추천상품 선택
		List<Product> recList = service.selectRecProduct(memberNo, productNo, smallcategoryNo);
		
		// 옵션 개수 선택
		List<ProductOption> optionList = service.selectOptionListName(productNo);
		//log.info("optionList : " + optionList);
		
		model.addAttribute("productInfo", productInfo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("smallCategoryList", smallCategoryList);
		model.addAttribute("bigcategoryNo", bigcategoryNo);
		model.addAttribute("selectRecProductList", recList);
		model.addAttribute("optionList", optionList);
		model.addAttribute("productNo", productNo);
		model.addAttribute("bigCategoryName", bigCategoryName);
		model.addAttribute("minPrice", "NO");
		model.addAttribute("memberNo", memberNo);

		
		
		return "eCommerce/eCommerceDetail";
	}
	
	
	
	/** 결제 페이지
	 * @param model
	 * @param session
	 * @param params
	 * @return
	 */
	@PostMapping("payment")
	public String eCommercePayment(Model model, HttpSession session,
								@RequestParam Map<String, String> params
								   ) {
		
	  
		
	    log.info("cartId : " + params);
	    // cartId : {check=on, quantity1=5, cartId=43, quantity2=5, quantity3=1, totalPrice=219905, cartIds=["43","45"]}
	    
	    // totalPrice=24, cartIds=["41","46"]}
	    
	    String cartIds = params.get("cartIds");
	    int memberNo = Integer.parseInt(params.get("memberNo"));
	    
	    log.info("꺼내온 cartId : " + cartIds);
	    log.info("꺼내온 memberNo : " + memberNo);
	    
	    // 주소 이슈로 직접 불러오기
		Member loginMember = service.selectMember(memberNo);
		
		if(loginMember != null) {
			model.addAttribute("loginMember", loginMember);
		}
		
		// 주소 포맷팅
	    if (loginMember.getMemberAddr() != null) {
	        String formattedAddr = loginMember.getMemberAddr().replaceAll("\\^\\^\\^", " ");
	        loginMember.setMemberAddr(formattedAddr);
	    }

		
	    // 장바구니 항목 불러오기
	    List<CartItem> cartItem = cartService.selectCartList(loginMember);

	    // 선택된 카트 아이디 추출
	    String selectedCartIdsParam = params.get("cartIds");
	    
	    List<Integer> selectedCartIds = new ArrayList<>();
	    
	    if (selectedCartIdsParam != null) {
	    	
	        String[] selectedCartIdsArray = selectedCartIdsParam.replaceAll("[\\[\\]\"]", "").split(",");
	        
	        for (String id : selectedCartIdsArray) {
	            selectedCartIds.add(Integer.parseInt(id.trim()));
	            
	        }
	    }

	    // 선택된 카트 항목 필터링
	    List<CartItem> selectedItems = new ArrayList<>();
	    
	    for (CartItem item : cartItem) {
	    	
	        if (selectedCartIds.contains(item.getCartId())) {
	        	
	            selectedItems.add(item);
	        }
	    }
		
	    log.info("loginMember : " + loginMember);
	    log.info("selectedItems : " + selectedItems);
	    log.info("totalPrice : " + params.get("totalPrice"));
	    
	    int totalPrice =  Integer.parseInt(params.get("totalPrice"));
	    
           
	    
	    // 모델에 필터링된 항목 추가)
	    model.addAttribute("cartList", selectedItems);
	    model.addAttribute("totalPrice", totalPrice);
	   
	    
	    
		return "eCommerce/eCommercePayment";
	}
	

	
	
	
	
	@PostMapping("complete")
	public String eCommerceComplete(@RequestParam Map<String, Object> params, @RequestParam("cartIds") String cartIdsJson, 
			Model model, RedirectAttributes ra, HttpSession session) {
		log.info("params : " + params);
		
		// 고유 주문 번호 추출
		String merchantUid = (String) params.get("merchantUid");
		
		// 배열로 전달 받은 형태
		// cartIds=["71","72"], 
	 	// productNos=["15","15"], 
	 	// productCounts=["1","1"], 
	 	// productPrices=["339000","339000"]}
	    // JSON 문자열을 배열로 변환
	    List<String> cartIds = Arrays.asList(cartIdsJson.replaceAll("[\\[\\]\"]", "").split(","));    
	    List<String> productNos = Arrays.asList(params.get("productNos").toString().replaceAll("[\\[\\]\"]", "").split(","));
	    List<String> productCounts = Arrays.asList(params.get("productCounts").toString().replaceAll("[\\[\\]\"]", "").split(","));
	    List<String> productPrices = Arrays.asList(params.get("productPrices").toString().replaceAll("[\\[\\]\"]", "").split(","));
	    
	   
        // --------------------- 옵션 추출 --------------------
        
	    String optionNosString = params.get("optionNos").toString();
	    optionNosString = optionNosString.substring(1, optionNosString.length() - 1);
	    List<String> optionNosList = Arrays.asList(optionNosString.split("\",\""));

	    // 앞뒤에 붙는 따옴표 제거
	    for (int i = 0; i < optionNosList.size(); i++) {
	        optionNosList.set(i, optionNosList.get(i).replaceAll("^\"|\"$", "").trim());
	    }

	    int size = optionNosList.size();
	    log.info("optionNosList 뭘까 ? : " + optionNosList);
	    log.info("optionNosList 사이즈는 뭘까 ? : " + size);

	    
	    // 옵션 추출 
        
	    // ORDER_DETAIL 테이블에 인서트된 횟수
	    int result = 0;
	    
	    // 고유 주문 번호로 주문 번호, 회원 번호 구해오기
	    Orders order = service.findOrder(merchantUid);
	    
	    if (order == null) {
	        log.error("Order not found for merchantUid: " + merchantUid);
	        model.addAttribute("message", "파라미터 안넘어옴");
	        
	        return "myPage/cart"; // 적절한 에러 페이지로 리다이렉트
	    }
	    
		int orderNo = order.getOrderNo();
		
		// 회원 번호는 포인트 적립 로직 구성
		int memberNo = order.getMemberNo();
		
		
	    // ORDERDETAIL_OPTION 테이블에 인서트된 횟수
	    int result2 = 0;
		
		// 1. 주문 상세 테이블 삽입
	    for (int i = 0; i < cartIds.size(); i++) {
	    	
	    	// 여기서 cartId는 반복문 도는 회수 용도로 사용
	        int cartId = Integer.parseInt(cartIds.get(i).trim());
	        int productNo = Integer.parseInt(productNos.get(i).trim());
	        int productCount = Integer.parseInt(productCounts.get(i).trim());
	        int productPrice = Integer.parseInt(productPrices.get(i).trim());
	        
	        log.info("cartId: " + cartId + ", productNo: " + productNo + ", productCount: " + productCount + ", productPrice: " + productPrice);
	        // cartId: 91, productNo: 21, productCount: 2, productPrice: 299000
	        // cartId: 92, productNo: 13, productCount: 3, productPrice: 77900
	        
	        // 1) 주문 상세 테이블에 반복 삽입
	        int insertResult = service.insertOrderDetail(orderNo, memberNo, cartId, productNo,productCount, productPrice);
	        
	        if(insertResult  > 0) {
	        	
	        	// 인서트 회수 증가
	        	result += insertResult;
	        	
		        // 우선 주문 상세의 ORDER_ITEM_NO를 구해오자
			    int orderItemNo = service.getOrderItemNo(orderNo, productNo, cartId);
	        	
			    log.info("주문 상세 테이블의 orderItemNo = {}", orderItemNo);
			    
		        // 2) 주문 상세 옵션 테이블에 반복 삽입
		        if (i < optionNosList.size()) {
		            String optionNoJson = optionNosList.get(i).replaceAll("[\\[\\]\"]", "");
		            List<Integer> optionNosForProduct = Arrays.stream(optionNoJson.split(","))
		                                                      .map(String::trim)
		                                                      .map(Integer::parseInt)
		                                                      .collect(Collectors.toList());

		            for (Integer optionNo : optionNosForProduct) {
		                log.info("포문 안에 옵션 넘버 : " + optionNo);

		                // 주문상세옵션 테이블 삽입
		                result2 += service.insertOrderDetailOption(orderItemNo, optionNo);
		            }
		        }

		        
	        }
	        
	    }

	    
	    if(result > 0) {
	    	
	    	log.info("주문 상세 테이블 정상 삽입 완료 : {}개", result);
	    	
	    }

	    if(result2 > 0) {
	    	
	    	log.info("주문 상세 옵션 테이블 정상 삽입 완료 : {}개", result2);
	    	
	    }
			   
		// 3. 장바구니 비워주기
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> cartIds2 = null;

        try {
            // JSON 문자열을 List<Integer>로 변환
            cartIds2 = objectMapper.readValue(cartIdsJson, new TypeReference<List<Integer>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 결제 후 장바구니 
        int result3 = 0;
        
        // cartIds가 제대로 추출되었는지 로그로 확인
        if (cartIds2 != null) {
            for (Integer deleteCartId : cartIds2) {
            	
                result3 += cartService.delProduct(deleteCartId);
                
            }
        }
        
        log.info("결제 완료 후 장바구니 삭제 개수 : " + result3 + "개");

       
        // 4. 실어줄 값 추출

		// 결제 수단, 결제 번호, 결제 일시, 그 외 구매자 정보...
		// PAYMENT에서 꺼내오기 merchantUid
		PaymentDTO payment = paymentService.selectPaid(merchantUid);
		
		
		// 5. 포인트 삽입하고 적립 포인트 띄워주기(추후 결제 리팩토링 시 포인트 차감 구현 시도)
        

		// 총 구매가의 1% 적립 포인트
		int point = (int) Math.floor(payment.getAmount() * 0.01 / 10) * 10;
        
		// 현재 포인트 구해서 + 처리하고 UPDATE
		int currentPoint = service.currentPoint(memberNo);
		
		
		int totalPoint = currentPoint + point;
		log.info("적립 포인트 1% = {} + 현재 포인트 {}  = 총 포인트 {}",  point, currentPoint, totalPoint);
				
		// UPDATE 
		int saveResult = service.savePoint(memberNo, totalPoint);  
		
		Member member = new Member();
		
		// 업데이트 성공 시 세팅
        if(saveResult > 0) {
        	
        	member.setMemberPoint(totalPoint);
        	
        	log.info("적립 성공! 멤버 객체 세팅 확인 : {}", member.getMemberPoint());
        	
        }
		
      
        
		// 5. 최종적으로 모델에 실어주기 
        // 따로 실어서 가져감
		// String merchantUid = (String) params.get("merchantUid");
		String deliveryMemo = (String) params.get("deliveryMemo");		
		
        
        // LocalDateTime을 yyyy-MM-dd HH:mm 형식의 문자열로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedPaidAt = payment.getPaidAt().format(formatter);
		
        
        // 리다이렉트 시 필요한 데이터 설정
        // 새로고침 시 데이터 사라지고 화이트라벨 뜸
        // 그래서 사용자 예외 발생을 방지하기 위해 세션에 담고
        // 리다이렉트 후 다른 메서드에서 뷰 반환
        
        session.setAttribute("merchantUid", merchantUid);
        session.setAttribute("deliveryMemo", deliveryMemo);
        session.setAttribute("paymentInfo", payment);
        session.setAttribute("point", point);
        session.setAttribute("myPoint", member.getMemberPoint());
        session.setAttribute("formattedPaidAt", formattedPaidAt);
        
        // 쇼핑상품 랜덤 4개 가져오기
 		List<Product> list = service.selectProduct6(memberNo);
 		
 		model.addAttribute("selectProductList", list);
      

		
		return "redirect:/eCommerce/completeResult";
	}
	
	// 렌더링
	@GetMapping("completeResult")
	public String eCommerceCompleteResult(HttpSession session, Model model) {
	    Object merchantUid = session.getAttribute("merchantUid");
	    Object deliveryMemo = session.getAttribute("deliveryMemo");
	    Object paymentInfo = session.getAttribute("paymentInfo");
	    Object formattedPaidAt = session.getAttribute("formattedPaidAt");
	    Object point = session.getAttribute("point");
	    Object myPoint = session.getAttribute("myPoint");

	    // 세션에 저장된 값이 null인지 확인하기 위해 로그 추가
	    log.info("Session values: merchantUid={}, deliveryMemo={}, paymentInfo={}, formattedPaidAt={}, point={}, myPoint={}",
	            merchantUid, deliveryMemo, paymentInfo, formattedPaidAt, point, myPoint);

	    // 모델에 값 추가
	    model.addAttribute("merchantUid", merchantUid);
	    model.addAttribute("deliveryMemo", deliveryMemo);
	    model.addAttribute("paymentInfo", paymentInfo);
	    model.addAttribute("formattedPaidAt", formattedPaidAt);
	    model.addAttribute("point", point);
	    model.addAttribute("myPoint", myPoint);


	    return "eCommerce/eCommerceComplete";
	}
	
	// 페이지 벗어날 경우 실어놨던 세션 제거
    @PostMapping("clearCompleteResultSession")
    @ResponseBody
    public void clearCompleteResultSession(HttpSession session) {
        log.info("clearCompleteResultSession 호출됨");

        session.removeAttribute("merchantUid");
        session.removeAttribute("deliveryMemo");
        session.removeAttribute("paymentInfo");
        session.removeAttribute("formattedPaidAt");
        session.removeAttribute("point");
        session.removeAttribute("myPoint");

        log.info("완료 페이지 벗어난 후 세션 제거 확인");
    }
	

	//[비동기]리뷰 조회하기
	@GetMapping("selectReviewList")
	@ResponseBody
	public List<Review> selectReviewList(@RequestParam("productNo") int productNo){
		
		return service.selectReviewList(productNo);
	}
	
	//해당 상품에 대한 리뷰 작성 가능 주문 상품 목록
	@GetMapping("checkReviewAuth")
	@ResponseBody
	public List<Order> checkReviewAuth(@RequestParam("productNo") int productNo,
								@SessionAttribute("loginMember") Member member) {
		log.info("상품번호{}, 회원번호{},결과{}",productNo, member.getMemberNo(),service.checkReviewAuth(productNo, member.getMemberNo()));
		return service.checkReviewAuth(productNo, member.getMemberNo());
	}
	
	

	//[비동기]리뷰 등록하기


	@PostMapping("reviewPost")
	@ResponseBody
	public int eCommercePostReview(@RequestParam("reviewContent") String reviewContent,
            						@RequestParam("reviewRating") int reviewRating,
            						@RequestParam("orderItemNo") int orderItemNo,
            						@RequestParam("productNo") int ProductNo,
									@RequestParam("reviewImgs") List<MultipartFile> reviewImgs,
									@RequestParam("optionValue") String optionValue,
									@SessionAttribute("loginMember") Member member ) throws IllegalStateException, IOException {


		int memberNo = member.getMemberNo();

		log.info("옵션이름{}",optionValue);
		Map<String, Object> map = new HashMap<>();
		map.put("reviewContent", reviewContent);
		map.put("reviewRating", reviewRating);
		map.put("orderItemNo", orderItemNo);
		map.put("ProductNo", ProductNo);
		map.put("memberNo", memberNo);
		map.put("optionValue", optionValue);


		int reviewNo = service.postReview(map);
		
		if(reviewNo == 0) {
			return 0;
		}else {//리뷰 사진 uploadFile에 삽입하기
			List<MultipartFile> imgList = new ArrayList<>(reviewImgs);

			int imgResult = service.insertImgs(reviewNo, reviewImgs);
			
			if(imgResult >0) {
				log.info("이미지 등록 개수:{}",imgResult);
				//등록 성공
				return imgResult;
			}else {
				return 0;
			}
		}



	}

	//[비동기]리뷰 삭제
	@DeleteMapping("delReview")
	@ResponseBody
	public int delReview(@RequestParam("reviewNo") int reviewNo) {
		return service.delReview(reviewNo);
	}
	
	//[비동기]수정할 리뷰 불러오기
	@GetMapping("reloadReview")
	@ResponseBody
	public Review reloadReview(@RequestParam("reviewNo") int reviewNo) {
		Review review = service.reloadReview(reviewNo);
		return review;
	}

	//[비동기] 리뷰 이미지 삭제하기
	@DeleteMapping("delImg")
	@ResponseBody
	public int delImg(@RequestBody Map<String, String> map) {

		//log.info("이미지 삭제 결과 : {}",service.delImg(map));
		return service.delImg(map);
	}
	
	
	//[비동기]리뷰 수정하기
	@PostMapping("updateReview")
	@ResponseBody
	public int updateReview(@ModelAttribute Review review,
							@RequestParam("reviewImgs") List<MultipartFile> reviewImgs) throws IllegalStateException, IOException{
		
		int result = service.updateReview(review,reviewImgs);
		if(result > 0) {
			return 1;
		}
		return 0;

	}
	


	//[비동기] 리뷰 개수
	@GetMapping("reviewCount")
	@ResponseBody
	public int reviewCount(@RequestParam("productNo") int productNo) {
		return service.reviewCount(productNo);
	}

	
	//[비동기]리뷰 평점
	@GetMapping("getAvgReviewScore")
	@ResponseBody
	public double avgScore(@RequestParam("productNo") int productNo) {
		return service.avgScore(productNo);
	}
	
	
	// 내 qna 보기
	@GetMapping("myQna")
	@ResponseBody
	public List<QNA> insertQna(@SessionAttribute("loginMember") Member loginMember) {
		
		return service.myQna(loginMember.getMemberNo());
	}
	// 모든 qna 보기
	@GetMapping("selectQna")
	@ResponseBody
	public List<QNA> selectQna() {
		
		return service.selectQna();
	}
	

	


	// qna 입력
	@PostMapping("insertQna")
	@ResponseBody
	public int insertQna(@RequestBody Map<String, Object> obj,
						@SessionAttribute("loginMember") Member loginMember) {
		
		log.info("obj : " + obj);

		obj.put("memberNo", loginMember.getMemberNo());
		
		return service.insertQna(obj);
	}
	
	

}
