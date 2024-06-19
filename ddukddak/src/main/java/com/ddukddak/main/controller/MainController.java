package com.ddukddak.main.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.service.MyHouseBoardService;
import com.ddukddak.board.model.service.tipBoardService;
import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.service.eCommerceService;
import com.ddukddak.member.model.dto.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
	
	private final eCommerceService eCommerceservice;
	private final MyHouseBoardService MyHouservice;
	private final tipBoardService tipService;

	// 메인페이지로 이동
	@RequestMapping("/")
	public String main(HttpServletRequest req,
						Model model) {
		
		HttpSession session = req.getSession();
		
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		int memberNo = 0;
		
		if(loginMember != null) {
			memberNo = loginMember.getMemberNo();
		}
		
		// 대분류 카테고리 선택
		List<Category> categoryList = eCommerceservice.selectCategory();
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("loginMember", loginMember);
		
		// 쇼핑상품 랜덤 4개 가져오기
		List<Product> list = eCommerceservice.selectProduct(memberNo);
		
		model.addAttribute("selectProductList", list);
		
		
		// 집들이 게시물 가져오기
		List<Board> MyHouseList = MyHouservice.selectMyHouseList();
		
		
		// 노하우 게시물 가져오기
		List<Board> tipList = tipService.selectTipList(2, "latest");
		
		List<Board> tipListTop3 = tipList.size() > 3 ? tipList.subList(0, 3) : tipList;
		
		
		model.addAttribute("myHouseList", MyHouseList);
		model.addAttribute("tipList", tipListTop3);
		
						
		return "common/main";
	}
	
	
	/** 회원 가입 페이지 이동(고객, 판매사, 시공사 선택)
	 * @return
	 */
	@RequestMapping("signup")
	public String signup() {
		return "common/signup";
	}
	
	@RequestMapping("loginError")
	public String loginError(RedirectAttributes ra) {
		ra.addFlashAttribute("message", "로그인 후 이용해주세요");
		return "redirect:/";
	}
	

	
}
