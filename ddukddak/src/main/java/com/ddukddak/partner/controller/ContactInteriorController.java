package com.ddukddak.partner.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.service.InteriorService;

import lombok.RequiredArgsConstructor;

@RequestMapping("interior")
@Controller
@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
public class ContactInteriorController {
	
	private final InteriorService service;

	@GetMapping("interiorList")
	public String interiorList(Model model) {
		
		List<Partner> interiorList = service.selectInteriorList();
		
		model.addAttribute("interiorList", interiorList);
		
		return "partner/interior/interiorList";
	}
	
	@GetMapping("interiorPortfolio")
	public String interiorPortfolio() {
		
		return "partner/interior/interiorPortfolio";
	}
	
	@GetMapping("interiorPortfolioDetail")
	public String interiorPortfolioDetail() {
		return "partner/interior/projectDetail";
	}
	
}
