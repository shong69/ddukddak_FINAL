package com.ddukddak.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("myCommunity")
@Controller
public class MyCommunityController {

	@GetMapping("myPageHouseBoard")
	public String myPageHouseBoard() {
		return "myPage/myPageHouseBoard";
	}
	@GetMapping("myPageTipBoard")
	public String myPageTipBoard() {
		return "myPage/myPageTipBoard";
	}
	@GetMapping("myLikes")
	public String myLikes() {
		return "myPage/myLikes";
	}
}
