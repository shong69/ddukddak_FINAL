 package com.ddukddak.board.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.service.MyHouseBoardService;
import com.ddukddak.board.model.service.tipBoardService;
import com.ddukddak.member.model.dto.Member;

import jakarta.servlet.ServletContext;
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
		
		ServletContext application = req.getServletContext();
		Map<String, Object> map = new HashMap<>();
		map.put("boardNo", boardNo);
		
		if (loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());
		}
		
		Board board = myHouseService.selectBoard(map);
		
		
		if (board == null) {
			
			ra.addFlashAttribute("message", "게시글이 존재하지 않습니다");
			
			return "redirect:/tip/main";
			
		} else {
			
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
				
			}
		}
		log.info("boardTypeList : " +  application.getAttribute("boardTypeList").toString());
		
		
		model.addAttribute("board", board);
		model.addAttribute("boardTypeList", application.getAttribute("boardTypeList"));
//		log.info("board : " + board.toString());
		
		
		return "board/tipBoard/tipBoardDetail";
	}
	
	@ResponseBody
	@PostMapping("like")
	public int boardLike(@RequestBody Map <String, Integer> map) {
		
		return service.tipLike(map);
	}
	
	@GetMapping("registTip")
	public String registMyHouse(@RequestParam("boardCode") int boardCode,
												Model model) {
		
		model.addAttribute("boardCode", boardCode);
		return "board/tipBoard/registTipBoard";
	}
	
	@PostMapping("registTip")
	public String registTipBoard(@SessionAttribute ("loginMember") Member loginMember,
												@RequestParam ("boardTitle") String inputTitle,
												@RequestParam ("boardContent") String inputContent,
												@RequestParam ("mainImg") String mainImgFileName,
												@RequestParam ("images") List<MultipartFile> images,
												RedirectAttributes ra) throws IOException{
		
		
		Board board = new Board();
		
	    board.setBoardTitle(inputTitle);
	    board.setBoardContent(inputContent);
	    board.setMemberNo(loginMember.getMemberNo());
	
	    MultipartFile mainImg = null;
	    
	    String message = null;
	    String path = null;
	    
	    
    	for (MultipartFile image : images) {
    		if (image.getOriginalFilename().equals(mainImgFileName)) {
    			mainImg = image;
//    			log.info("mainImg : " + mainImg);
    			break;
    		}
    	}
    	
    	List<MultipartFile> imgList = new ArrayList<>(images);
    	
    	imgList.remove(mainImg);	// 중복된 mainImg 리스트에서 삭제
    	
    	imgList.add(0, mainImg);	// 다시 mainImg 를 배열 0번째 자리에 추가
    	
    	int boardNo = service.insertBoard(board, imgList);
    	
//    	log.info("imgList : " + imgList);
    	
    	
    	if(boardNo > 0) {
  		
    		// 후에 리스트, 상세페이지 다 되면 상세페이지로 넘어가게 수정
    		path= "detail/" + boardNo;
    		message = "노하우 게시글 등록이 완료되었습니다.";
    		
    		
    	} else {
    		
    		path = "main";
    		message = "노하우 게시글 등록에 실패하였습니다.";
    		
    	}
	    

	    	
	    
	    ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	@PostMapping("deleteTip")
	@ResponseBody
	public int deleteTip(@RequestParam("boardNo") int boardNo,
							 @RequestParam("boardCode") int boardCode,
							 RedirectAttributes ra) {
		
		log.info("boardNo : " + boardNo);

		return service.deleteTip(boardNo);
	}
	
	@GetMapping("updateTip")
	public String updateMyHouse(@RequestParam("boardNo") int boardNo,
								@SessionAttribute(value="loginMember", required=false) Member loginMember,
								RedirectAttributes ra,
								Model model) {
		
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("boardNo", boardNo);
		
		Board board = myHouseService.selectBoard(map);
		
		if(loginMember == null || loginMember.getMemberNo() != board.getMemberNo()) {
			ra.addFlashAttribute("message", "접근 권한이 없습니다.");
			return "redirect:/tip/detail/" + boardNo;
		}
		
		log.info("이미지 : " + board.getImageList());
		
		model.addAttribute("board", board);
		
		return "board/tipBoard/updateTip";
	}
	
	

//	@PostMapping("updateTip")
//	public String updateTip(@RequestParam("boardNo") int boardNo,
//								@RequestParam("boardTitle") String inputBoardTitle,
//								@RequestParam("boardContent") String inputBoardContent,
//								@RequestParam("images") List<MultipartFile> images,
//								@SessionAttribute("loginMember") Member loginMember,
//								RedirectAttributes ra) throws IllegalStateException, IOException {
//		
//		Board board = new Board();
//		
//		board.setBoardNo(boardNo);
//		board.setBoardTitle(inputBoardTitle);
//		board.setBoardContent(inputBoardContent);
//		


//	@PostMapping("updateTip")
//	public String updateTip(@RequestParam("boardNo") int boardNo,
//								@RequestParam("boardTitle") String inputBoardTitle,
//								@RequestParam("boardContent") String inputBoardContent,
//								@RequestParam("images") List<MultipartFile> images,
//								@SessionAttribute("loginMember") Member loginMember,
//								RedirectAttributes ra) throws IllegalStateException, IOException {
//		
//		Board board = new Board();
//		
//		board.setBoardNo(boardNo);
//		board.setBoardTitle(inputBoardTitle);
//		board.setBoardContent(inputBoardContent);

//		int result = myHouseService.updateMyHouse(board, images);
//		String path = null;
//		String message = null;
//		
//		if(result > 0) {
//			path = "/tip/detail/" + boardNo;
//			message = "노하우 게시글 수정이 완료되었습니다.";
//		} else {
//			path = "/tip/updateMyHouse";
//			message = "노하우 게시글 수정에 실패하였습니다.";
//		}
//		
//		ra.addFlashAttribute("message", message);
//		
//		return "redirect:" + path;

//	}

//
//
//	@PostMapping("updateTip")
//	public String updateTip(@RequestParam("boardNo") int boardNo,
//							@RequestParam("boardTitle") String inputBoardTitle,
//							@RequestParam("boardContent") String inputBoardContent,
//							@RequestParam("images") List<MultipartFile> images,
//							@SessionAttribute("loginMember") Member loginMember,
//							RedirectAttributes ra) throws IllegalStateException, IOException {
//		
//		Board board = new Board();
//		
//		board.setBoardNo(boardNo);
//		board.setBoardTitle(inputBoardTitle);
//		board.setBoardContent(inputBoardContent);
//		
//  }
//
//
}














