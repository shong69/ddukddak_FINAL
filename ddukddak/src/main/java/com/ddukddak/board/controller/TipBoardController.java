package com.ddukddak.board.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.service.MyHouseBoardService;
import com.ddukddak.board.model.service.tipBoardService;
import com.ddukddak.member.model.dto.Member;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("tip")
@RequiredArgsConstructor
public class TipBoardController {

	private final tipBoardService service;
	
	private final MyHouseBoardService myHouseService;
	
	@GetMapping("main")
	public String tipBoardMain(@RequestParam("boardCode") int boardCode,
			  @RequestParam(value="sort", required = false, defaultValue = "latest") String sort,
			  Model model,
			  @RequestParam(value="query", required = false) String query) {
		
	List<Board> tipBoard = null;
		
		// 검색 X
		if(query == null) {
			
			
			tipBoard = service.selectTipList(boardCode, sort);
			
			
		} else {	// 검색 O
			
			tipBoard = service.searchList(boardCode, sort, query);
			
		}
		
		List<List<Board>> tipBoardChunks = chunkBoards(tipBoard, 4);
		
//		for (Board tip : tipBoard) {
//			log.info("boardNo : " + tip.getBoardNo());
//		}
//		
//		for (List<Board> tip : tipBoardChunks) {
//			for (Board btip : tip) {
//				log.info("boardNo inside chunks : " + btip.getBoardNo());
//			}
//		}
		
		model.addAttribute("boardCode", boardCode);
		model.addAttribute("tipBoardChunks", tipBoardChunks);
		model.addAttribute("query", query);
		
		return "board/tipBoard/tipBoard";
	}
	
	private List<List<Board>> chunkBoards(List<Board> boards, int chunkSize) {
        List<List<Board>> boardChunks = new ArrayList<>();
        for (int i = 0; i < boards.size(); i += chunkSize) {
            boardChunks.add(boards.subList(i, Math.min(i + chunkSize, boards.size())));
        }
        return boardChunks;
    }
	
	@GetMapping("detail/{boardNo:[0-9]+}")
	public String tipBoardDetail(@PathVariable("boardNo") int boardNo, 
												@SessionAttribute(value="loginMember", required=false) Member loginMember, 
												Model model, 
												RedirectAttributes ra,
												HttpServletRequest req,
												HttpServletResponse resp) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardNo", boardNo);
		
		if (loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());
		}
		
		Board board = myHouseService.selectBoard(map);
		
		
		if (board == null) {
			
			ra.addFlashAttribute("message", "게시글이 존재하지 않습니다");
			
			return "redirect:/tip/main";
			
		}
		
		if (loginMember == null || loginMember.getMemberNo() != board.getMemberNo()) {
			
			Cookie[] cookies = req.getCookies();
			
			Cookie c = null;
			
			if(cookies != null) {

				for(Cookie temp : cookies) {
					if (temp.getName().equals("readBoardNo" )) {
						c = temp;
						break;
					}
				}
			}
			
			int result = 9;
			
			if (c == null) {
				
				c = new Cookie("readrBoardNo", "["+boardNo+"]");
				
				result = myHouseService.updateReadCount(boardNo);
			} else {
                String value = c.getValue();
                if (!value.contains("[" + boardNo + "]")) {
                    value += "[" + boardNo + "]";
                    c.setValue(value);
                    result = myHouseService.updateReadCount(boardNo);
                }
            }
			
			if (result > 0) {
				board.setReadCount(result);
							
				LocalDateTime now = LocalDateTime.now();
				
				LocalDateTime nextDayMidnight = now.plusDays(1).withHour(0).withMinute(0).withSecond(result).withNano(0);
				
				long secondsUntilNextDay = Duration.between(now, nextDayMidnight).getSeconds();
				
				c.setPath("/");

				c.setMaxAge((int)secondsUntilNextDay);
				
				resp.addCookie(c);
			}
			
			model.addAttribute("board", board);
			
		}
		
		return "board/tipBoard/tipBoardDetail";
	}
	
	@ResponseBody
	@PostMapping("/like")
	public int boardLike(@RequestBody Map <String, Integer> map) {
		
		return service.tipLike(map);
	}
	
	
	@GetMapping("registTipBoard")
	public String registTipBoard() {
		
		
		
		return "board/tipBoard/registTipBoard";
	}
	
}
