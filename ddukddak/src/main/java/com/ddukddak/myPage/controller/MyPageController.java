package com.ddukddak.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("myPage")
public class MyPageController {

	@GetMapping("")
	public String main() {
		return"myPage/myPageMain";
	}
	
	@GetMapping("wishList")
	public String wishList() {
		return"myPage/wishList";
	}
}
