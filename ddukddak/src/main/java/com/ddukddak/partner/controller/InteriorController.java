package com.ddukddak.partner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("partner")
@Controller
@RequiredArgsConstructor
public class InteriorController {
	
	@GetMapping("interiorList")
	public String interiorList() {
		
		return "partner/interior/interiorList";
		
	}

	@GetMapping("interiorPortfolio")
	public String interiorPortfolio() {
		return "partner/interior/interiorPortfolio";
	}

	//모델링
	@GetMapping("interior/modeling/main")
	public String modelingMain() {
		return "partner/interior/modeling/modelingMain";
	}
	
	@GetMapping("interior/modeling/estimate")
	public String modelingEstimate() {
		return "partner/interior/modeling/modelingEstimate";
	}
	
	@GetMapping("interiorPortfolioDetail")
	public String interiorPortfolioDetail() {
		return "partner/interior/interiorPortfolioDetail";
	}
	
	
	@GetMapping("interiorPortfolioEditMain")
	public String interiorPortfolioEditMain() {
		return "partner/interior/interiorPortfolioEdit/interiorPortfolioEditMain";
	}
	
	@GetMapping("projectDetail")
	public String projectDetail() {
		return "partner/interior/interiorPortfolioEdit/projectDetail";
	}
	
	@GetMapping("interiorChatWithManager")
	public String interiorChatWithManager() {
		return "partner/interior/interiorChatWithManager/interiorChatWithManager";
	}
	
	@GetMapping("interiorChatWithManagerPopup")
	public String interiorChatWithManagerPopup() {
		return "partner/interior/interiorChatWithManager/interiorChatWithManagerPopup";
	}
	
	@GetMapping("interiorChatWithUser")
	public String interiorChatWithUser() {
		return "partner/interior/interiorChatWithUser/interiorChatWithUser";
	}
	
}
