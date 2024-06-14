package com.ddukddak.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.dto.BoardImg;
import com.ddukddak.board.model.dto.Report;
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
		
		List<Board> allBoard = service.selectBoard();
		
	
		List<Board> houseBoard = allBoard.stream().filter(board -> board.getBoardCode() == 1).collect(Collectors.toList());
		List<Board> tipBoard = allBoard.stream().filter(board -> board.getBoardCode() == 2).collect(Collectors.toList());
		
//		log.info("tipBoard : " + tipBoard.toString());
//		log.info("houseBoard : " + houseBoard.toString());
		
		 // Chunk the filtered boards
        List<List<Board>> houseBoardChunks = chunkBoards(houseBoard, 6);
        List<List<Board>> tipBoardChunks = chunkBoards(tipBoard, 4);
		
		if (allBoard != null) {
			
			
			 // Add the board chunks to the model
	        model.addAttribute("houseBoardChunks", houseBoardChunks);
	        model.addAttribute("tipBoardChunks", tipBoardChunks);
	        
//	        log.info("tipBoardChunks : " + tipBoardChunks.toString());
//	        log.info("houseBoardChunks : " + houseBoardChunks.toString());
		}
		
		return "board/boardMainPage";
	}
	
	private List<List<Board>> chunkBoards(List<Board> boards, int chunkSize) {
        List<List<Board>> boardChunks = new ArrayList<>();
        for (int i = 0; i < boards.size(); i += chunkSize) {
            boardChunks.add(boards.subList(i, Math.min(i + chunkSize, boards.size())));
        }
        return boardChunks;
    }
	
	
	/** 게시글 좋아요 체크/해제
	 * @param map
	 * @return count
	 */
	@ResponseBody
	@PostMapping("like")
	public int boardLike(@RequestBody Map<String, Integer> map) {
		
		return service.boardLike(map);
	}
	
	
	
	/** 신고 등록
	 * @return
	 */
	@PostMapping("report")
	@ResponseBody
	public int report(@RequestBody Report report) {
		
		log.info("report : " + report);
		
		return service.insertReport(report);
	}
}
