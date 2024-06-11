package com.ddukddak.ecommerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpRequest;
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

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.DetailProduct;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductOption;
import com.ddukddak.ecommerce.model.service.eCommerceService;
import com.ddukddak.member.model.dto.Member;

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

	@GetMapping("main")
	public String eCommerceMain(@RequestParam(value="cp", required=false, defaultValue="1") int cp,
								@RequestParam(value="query", required=false) String query,
								Model model) {
		
		// 대분류 카테고리 선택
		List<Category> categoryList = service.selectCategory();
		
		model.addAttribute("categoryList", categoryList);
		
		if(query != null) {
			Map<String, Object> map = service.searchList(query, cp);
			
			int bigcategoryNo = 1;
			int smallcategoryNo = 1;
			
			model.addAttribute("selectProductList", map.get("productList"));
			model.addAttribute("pagination", map.get("pagination"));
			model.addAttribute("bigCategoryNo", bigcategoryNo);
			model.addAttribute("smallCategoryNo", smallcategoryNo);
			model.addAttribute("query", query);
			
			return "/eCommerce/eCommerceList";
		} else {
			List<Product> list = service.selectProduct();
			List<Product> bestList = service.selectBestProduct();
			
			
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
								Model model) {
		
		Map<String, Object> map = null;
		
		if(query == null) { //검색 X(그냥 목록 조회)

			map = service.selectProductList(smallcategoryNo, cp);
			
		}else { //검색 O
			
			map = service.searchList(query, cp);
			
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
		model.addAttribute("query", query);
		
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
		
		DetailProduct productInfo = service.selectOneProduct(productNo);
		
		// 대분류 카테고리 선택
		List<Category> categoryList = service.selectCategory();
				
		// 소분류 카테고리 선택
		List<Category> smallCategoryList = service.selectSmallCategory(bigcategoryNo);
		
		// 추천상품 선택
		List<Product> recList = service.selectRecProduct(productNo, smallcategoryNo);
		
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


		HttpSession session = req.getSession();
		
		Member loginMember = (Member)session.getAttribute("loginMember");
		model.addAttribute("loginMember", loginMember);
		
		
		return "eCommerce/eCommerceDetail";
	}
	
	
	@RequestMapping("payment")
	public String eCommercePayment() {
		return "eCommerce/eCommercePayment";
	}
	
	@RequestMapping("complete")
	public String eCommerceComplete() {
		return "eCommerce/eCommerceComplete";
	}
	
	
}
