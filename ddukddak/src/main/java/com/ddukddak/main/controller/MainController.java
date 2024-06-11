package com.ddukddak.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {


	@RequestMapping("/")
	public String main() {
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
