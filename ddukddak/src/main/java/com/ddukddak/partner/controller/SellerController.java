package com.ddukddak.partner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.partner.model.dto.Partner;

@RequestMapping("partner/seller")
@Controller
@SessionAttributes({"loginPartnerMember"})
public class SellerController {

	@GetMapping("product/create")
	public String ProductCreate(@SessionAttribute("loginPartnerMember") Partner loginPartnerMember, RedirectAttributes ra) {
		
		if (loginPartnerMember.getPartnerType() != 2) {
            ra.addFlashAttribute("message", "접근 제한된 서비스 입니다");
            return "redirect:/partner/main";
        } 
        
        return "partner/seller/product/create";
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
