package com.ddukddak.ecommerce.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.DetailProduct;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductOption;
import com.ddukddak.ecommerce.model.service.eCommerceService;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.myPage.model.dto.CartItem;
import com.ddukddak.myPage.model.service.CartAndWishListService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("eCommerce")
@RequiredArgsConstructor
@Slf4j
public class eCommerceController {
	
	// 쇼핑몰 메인페이지
	private final eCommerceService service;
	private final CartAndWishListService cartService;
	
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
			
			return "/eCommerce/eCommerceList";
		} else {
			List<Product> list = service.selectProduct(memberNo);
			List<Product> bestList = service.selectBestProduct(memberNo);
			
			
			model.addAttribute("selectProductList", list);
			model.addAttribute("selectBestProductList", bestList);
			
			
			return "/eCommerce/eCommerceMain";		
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
		
		return "/eCommerce/eCommerceList";
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
		log.info("optionList : " + optionList);
		
		model.addAttribute("productInfo", productInfo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("smallCategoryList", smallCategoryList);
		model.addAttribute("bigcategoryNo", bigcategoryNo);
		model.addAttribute("selectRecProductList", recList);
		model.addAttribute("optionList", optionList);
		model.addAttribute("productNo", productNo);
		model.addAttribute("bigCategoryName", bigCategoryName);
		model.addAttribute("minPrice", "NO");

		
		
		return "eCommerce/eCommerceDetail";
	}
	
	
	
	@RequestMapping("payment")
	public String eCommercePayment(Model model, HttpSession session,
								@RequestParam Map<String, String> params
								   ) {
		
	  
		
	    log.info("cartId : " + params);
	    // cartId : {check=on, quantity1=5, cartId=43, quantity2=5, quantity3=1, totalPrice=219905, cartIds=["43","45"]}
	    
	    // totalPrice=24, cartIds=["41","46"]}
	    
	    String cartIds = params.get("cartIds");
	    
	    log.info("꺼내온 cartId : " + cartIds);
	    
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		
		
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
		
	    log.info("selectedItems : " + selectedItems);
	    log.info("totalPrice : " + params.get("totalPrice"));
	    
	    
	    // 주문 정보 생성

	    
	    // 모델에 필터링된 항목 추가
	    model.addAttribute("cartList", selectedItems);
	    model.addAttribute("totalPrice", params.get("totalPrice"));

		
		return "eCommerce/eCommercePayment";
	}
	
	
	@RequestMapping("complete")
	public String eCommerceComplete() {
		
		
		
		return "eCommerce/eCommerceComplete";
	}
	

	//[비동기]리뷰 조회하기
	//[비동기]리뷰 삽입하기
	@PostMapping("reviewPost")
	@ResponseBody
	public String eCommercePostReview(@RequestParam("reviewContent") String reviewContent,
										@RequestParam("reviewRating") int reviewRating,
										@RequestParam("reviewImgs") List<MultipartFile> reviewImgs,
										@SessionAttribute("loginMember") Member loginMember) throws IOException {
		return "";
	}
	
	
	
	
	
	
	
	
	

}
