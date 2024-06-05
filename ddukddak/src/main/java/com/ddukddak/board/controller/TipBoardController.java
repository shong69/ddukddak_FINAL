package com.ddukddak.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ddukddak.board.model.service.BoardMainService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("tip")
@RequiredArgsConstructor
public class TipBoardController {

	private final BoardMainService service;
	
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
