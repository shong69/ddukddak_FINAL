package com.ddukddak.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tip")
public class TipBoardController {

	@GetMapping("main")
	public String tipBoardMain() {
		return "board/tipBoard/tipBoard";
	}
	
	
	@GetMapping("detail")
	public String tipBoardDetail() {
		return "board/tipBoard/tipBoardDetail";
	}
	
	@GetMapping("registTipBoard")
	public String registTipBoard() {
		return "board/tipBoard/registTipBoard";
	}
}
