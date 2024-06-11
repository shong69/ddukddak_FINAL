package com.ddukddak.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.dto.BoardImg;
import com.ddukddak.board.model.service.BoardMainService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@RequiredArgsConstructor
@Slf4j
public class BoardMainController {
	
	private final BoardMainService service;

	@GetMapping("main")
	public String boardMain(Model model) {
		
		Map<String, Object> map = new HashMap<>();
		
		List<Map<String, Object>> boardTypeList = service.selectBoardTypeList();
		
		List<BoardImg> boardImg = service.selectBoardMainAd();
		
		if (boardImg != null) {
			
			
			model.addAttribute("boardImg", boardImg);
			
		}
		
		List<Board> board = service.selectBoard();
		
	
		if (board != null) {
			
			List<List<Board>> boardChunks = new ArrayList<>();
			int chunkSize = 6;
			for (int i = 0; i < board.size(); i += chunkSize) {
				boardChunks.add(board.subList(i, Math.min(i + chunkSize, board.size())));
			}
			
			model.addAttribute("board",board);
			model.addAttribute("boardChunks",boardChunks);
		}
		
		
		
		for(Map<String, Object> boardType : boardTypeList) {
			map.put(boardType.get("boardCode").toString(), boardType.get("boardName"));
		}
		
//		log.info("map : " + map); 
		
		model.addAttribute("boardTypeMap", map);
		
		
		return "board/boardMainPage";
	}
	
}
