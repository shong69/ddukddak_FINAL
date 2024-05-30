package com.ddukddak.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("eCommerce")
public class eCommerceController {

	@RequestMapping("main")
	public String eCommerceMain() {
		return "/eCommerce/eCommerceMain";
	}
	
	@RequestMapping("list")
	public String eCommerceList() {
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
