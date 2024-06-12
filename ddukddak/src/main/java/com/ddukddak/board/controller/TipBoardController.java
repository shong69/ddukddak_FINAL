package com.ddukddak.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.service.tipBoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("tip")
@RequiredArgsConstructor
public class TipBoardController {

	private final tipBoardService service;
	
	@GetMapping("main")
	public String tipBoardMain(@RequestParam("boardCode") int boardCode,
			  @RequestParam(value="sort", required = false, defaultValue = "latest") String sort,
			  Model model,
			  @RequestParam(value="query", required = false) String query) {
		
	List<Board> tipBoard = null;
		
		// 검색 X
		if(query == null) {
			
			log.info("sort : " + sort);
			
			tipBoard = service.selectTipList(boardCode, sort);
			
		} else {	// 검색 O
			
			tipBoard = service.searchList(boardCode, sort, query);
			
		}
		
		model.addAttribute("boardCode", boardCode);
		model.addAttribute("tipBoard", tipBoard);
		model.addAttribute("query", query);
		
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
