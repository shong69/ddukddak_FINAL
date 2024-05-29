package com.ddukddak.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("board")
public class BoardMainController {

	@GetMapping("main")
	public String boardMain() {
		return "board/boardMainPage";
	}
	
}
