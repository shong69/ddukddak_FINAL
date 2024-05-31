package com.ddukddak.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("myHouse")
public class MyHouseBoardController {

	@GetMapping("main")
	public String myHouseMain() {
		return "board/myHouseBoard/myHouseBoard";
	}
	
	@GetMapping("detail")
	public String myHouseDetail() {
		return "board/myHouseBoard/myHouseBoardDetail";
	}
	
	@GetMapping("registMyHouse")
	public String registtMyHouse() {
		return "board/myHouseBoard/registMyHouse";
	}
	
}
