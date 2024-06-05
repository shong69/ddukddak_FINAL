package com.ddukddak.ecommerce.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@RequestMapping("main")
	public String eCommerceMain(Model model) {
		
		List<Product> list = service.selectProduct();
		List<Product> bestList = service.selectBestProduct();
		
		model.addAttribute("selectProductList", list);
		model.addAttribute("selectBestProductList", bestList);
		
		
		return "/eCommerce/eCommerceMain";
	}
	
	@RequestMapping("list")
	public String eCommerceList(Model model) {
		
		List<Product> list = service.selectProductList();
		
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
