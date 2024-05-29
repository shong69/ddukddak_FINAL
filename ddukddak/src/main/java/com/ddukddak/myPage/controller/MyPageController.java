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
	
	@GetMapping("cart")
	public String cart() {
		return "myPage/cart";
	}
	@GetMapping("myLikes")
	public String myLikes() {
		return "myPage/myLikes";
	}
	@GetMapping("chatWithInter")
	public String chatWithInter() {
		return "myPage/chatWithInter";
	}
	@GetMapping("myPageHouseBoard")
	public String myPageHouseBoard() {
		return "myPage/myPageHouseBoard";
	}
	@GetMapping("myPageTipBoard")
	public String myPageTipBoard() {
		return "myPage/myPageTipBoard";
	}
	
}
