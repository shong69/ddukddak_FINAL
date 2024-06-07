package com.ddukddak.ecommerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ddukddak.ecommerce.model.dto.BigCategory;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.service.eCommerceService;

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
	public String eCommerceMain(Model model) {
		log.debug("## MAIN ##");
		List<Product> list = service.selectProduct();
		List<Product> bestList = service.selectBestProduct();
		
		List<BigCategory> categoryList = service.selectCategory();
		
		model.addAttribute("selectProductList", list);
		model.addAttribute("selectBestProductList", bestList);
		model.addAttribute("categoryList", categoryList);
		
		
		return "/eCommerce/eCommerceMain";
	}
	
	@GetMapping("list/{categoryNo:[0-9]+}")
	public String eCommerceList(@PathVariable("categoryNo") int categoryNo,
								Model model) {
		Map<String,Integer> map = new HashMap<String, Integer>();
		
		map.put("categoryNo", categoryNo);
		
		List<Product> list = service.selectProductList(categoryNo);
		
		model.addAttribute("selectProductList", list);
		
		return "/eCommerce/eCommerceList";
	}
	
	@RequestMapping("detail")
	public String eCommerceDetail() {
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
