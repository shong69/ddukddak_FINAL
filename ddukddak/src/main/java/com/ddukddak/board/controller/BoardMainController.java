package com.ddukddak.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("board")
public class BoardMainController {

	@GetMapping("main")
	public String boardMain() {
		
		return "board/boardMainPage";
	}
	
}
