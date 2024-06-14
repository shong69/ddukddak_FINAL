package com.ddukddak.partner.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.service.eCommerceService;
import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.service.PartnerService;
import com.ddukddak.partner.model.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("partner/seller")
@Controller
@RequiredArgsConstructor
@Slf4j
//@SessionAttributes("{loginPartnerMember}")
public class SellerController {
	
	private final ProductService service;
	private final eCommerceService eCommerceService;

	@GetMapping("product/create")
	public String ProductCreate(
			//@SessionAttribute("loginPartnerMember") Partner loginPartnerMember, 
			RedirectAttributes ra,
			Model model) {
		
//		if (loginPartnerMember.getPartnerType() != 2) {
//            ra.addFlashAttribute("message", "접근 제한된 서비스 입니다");
//            return "redirect:/partner/main";
//        } 
		
		// 대분류 카테고리 선택
		List<Category> categoryList = eCommerceService.selectCategory();
		
		model.addAttribute("categoryList", categoryList);
		
		// 소분류 카테고리 선택
		List<Category> smallCategoryList = eCommerceService.selectSmallCategory();
		
		model.addAttribute("smallCategoryList", smallCategoryList);
		
		// 재고상품 조회
		List<Product> createList = service.selectCreateList();
		
		model.addAttribute("createList", createList);

        
        return "partner/seller/product/create";
	}
	
	@PutMapping("product/sellApplyProduct")
	@ResponseBody
	public int sellApplyProduct(@RequestBody List<Object> selectedValues) {
		
		int result = service.sellApplyProduct(selectedValues);
		
		return result;
	}
	
	@PostMapping("product/selectSmallCategory")
	@ResponseBody
	public List<Category> selectSmallCategory(@RequestBody int selectedCategory) {
		
		List<Category> smallCategoryList = service.selectSmallCategory(selectedCategory);
		
		return smallCategoryList;
	}
	
	@GetMapping("product/apply")
	public String ProductApply() {
		return "partner/seller/product/apply";
	}
	
	@GetMapping("product/applyProduct")
	public String ProductApplyProduct() {
		return "partner/seller/product/applyProduct";
	}
	
	@GetMapping("product/receipt")
	public String ProductReceipt() {
		return "partner/seller/product/receipt";
	}
	
	@GetMapping("product/shipment")
	public String ProductRelease() {
		return "partner/seller/product/shipment";
	}
	
	@GetMapping("product/complete")
	public String ProductComplete() {
		return "partner/seller/product/complete";
	}
	
	
	
	
	@GetMapping("qna")
	public String ProductQNA() {
		return "partner/seller/qna/qna";
	}
}
