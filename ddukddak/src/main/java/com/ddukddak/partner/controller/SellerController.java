package com.ddukddak.partner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("partner/seller")
@Controller
public class SellerController {

	@GetMapping("product/create")
	public String ProductCreate() {
		return "partner/seller/product/create";
	}
	
	@GetMapping("product/apply")
	public String ProductApply() {
		return "partner/seller/product/apply";
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