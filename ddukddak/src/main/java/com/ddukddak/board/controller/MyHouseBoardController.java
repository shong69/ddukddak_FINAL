package com.ddukddak.board.controller;


import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.service.MyHouseBoardService;
import com.ddukddak.member.model.dto.Member;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("myHouse")
@RequiredArgsConstructor
@Slf4j
public class MyHouseBoardController {
	
	private final MyHouseBoardService service;

	@GetMapping("main")
	public String myHouseMain(@RequestParam("boardCode") int boardCode,
							  @RequestParam(value="cp", required = false, defaultValue = "1") int cp,
							  @RequestParam(value="sort", required = false, defaultValue = "latest") String sort,
							  Model model,
							  @RequestParam(value="query", required = false) String query) {
		
		Map<String, Object> map = null;
		
		// 검색 X
		if(query == null) {
			
			
			map = service.selectMyHouseList(boardCode, sort, cp);
			
		} else {	// 검색 O
			
			map = service.searchList(boardCode, sort, query, cp);
			
		}
		
		model.addAttribute("boardCode", boardCode);
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("myHouseList", map.get("myHouseList"));
		model.addAttribute("query", query);
		model.addAttribute("sort", sort);
		
		return "board/myHouseBoard/myHouseBoard";
	}
	
	@GetMapping("detail/{boardNo:[0-9]+}")
	public String myHouseDetail(@PathVariable("boardNo") int boardNo,
								@SessionAttribute(value="loginMember", required=false) Member loginMember,
								Model model,
								RedirectAttributes ra,
								HttpServletRequest req,
								HttpServletResponse resp) {
		
		ServletContext application = req.getServletContext();
		
		log.info("보드타입 리스트: " + application.getAttribute("boardTypeList"));
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardNo", boardNo);
		
		if(loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());
		}
		
		Board board = service.selectBoard(map);
		
		String path = null;
		
		// 조회 결과 X
		if(board == null) {
			
			path = "redirect:/myHouse/main";		// 집들이 게시판 재요청
			ra.addFlashAttribute("message", "게시글이 존재하지 않습니다.");
			
		} else {	// 조회 결과 O (게시글 있는 경우)
			
			// 로그인 상태가 아니거나 로그인한 회원의 게시글이 아닌 경우
			if(loginMember == null || loginMember.getMemberNo() != board.getMemberNo()) {
				
				Cookie[] cookies = req.getCookies();
				
				Cookie c = null;
				
				for(Cookie temp : cookies) {
					// 요청에 담긴 쿠키에 "readBoardNo" 가 존재할 때
					if(temp.getName().equals("readBoardNo")) {
						c = temp;
						break;
					}
					
				}
				
				int result = 0;		// 조회수 증가 결과 저장 변수
				
				if(c == null) {	// 쿠키에 "readBoardNo" 값이 없을 때
					
					c = new Cookie("readBoardNo", "["+boardNo+"]");
					result = service.updateReadCount(boardNo);
					
				} else {	// "readBoardNo" 값이 있을 때
					
					if(c.getValue().indexOf("[" + boardNo + "]") == -1) {
						// indexOf("문자열") : 찾는 문자열의 인덱스 번호를 반환, 찾는 문자열이 없다면 -1 반환
						
						// 해당 글을 처음 읽은 경우
						c.setValue(c.getValue() + "[" + boardNo + "]");
						result = service.updateReadCount(boardNo);
					}
					
				}
				
				// 조회수 증가 성공 / 조회 성공
				if(result > 0) {
					
					// 먼저 조회된 board 의 readCount 값을 result 값으로 변환(재대입)
					board.setReadCount(result);
					
					// 적용 경로 설정
					c.setPath("/");		// "/" 이하 경로 요청시 쿠키 서버로 전달
					
					LocalDateTime now = LocalDateTime.now(); 
					
					LocalDateTime nextDayMidnight = now.plusDays(1).withHour(0).withMinute(0).withSecond(result).withNano(0);
					
					long secondsUntilNextDay = Duration.between(now, nextDayMidnight).getSeconds();
					
					c.setMaxAge( (int) secondsUntilNextDay );
					
					resp.addCookie(c);
					
				}
				
			}
			
			path = "board/myHouseBoard/myHouseBoardDetail";
			
			model.addAttribute("board", board);
			model.addAttribute("boardTypeList", application.getAttribute("boardTypeList"));
		}
		
				
		return path;
	}
	
	@GetMapping("registMyHouse")
	public String registMyHouse(@RequestParam("boardCode") int boardCode,
								Model model) {
		
		log.info("boardCode : " + boardCode);
		
		model.addAttribute("boardCode", boardCode);
		return "board/myHouseBoard/registMyHouse";
	}
	
	// 집들이 게시글 등록
	@PostMapping("registMyHouse")
	public String registMyHouse(@SessionAttribute ("loginMember") Member loginMember,
								@RequestParam ("boardTitle") String inputTitle,
								@RequestParam ("boardContent") String inputContent,
								@RequestParam ("mainImg") String mainImgFileName,
								@RequestParam ("images") List<MultipartFile> images,
								RedirectAttributes ra) throws IOException {
		
		
		Board board = new Board();
		
	    board.setBoardTitle(inputTitle);
	    board.setBoardContent(inputContent);
	    board.setMemberNo(loginMember.getMemberNo());
	
	    MultipartFile mainImg = null;
	    
	    String message = null;
	    String path = null;
	    
	    log.info("images : " + images);
	    
    	for (MultipartFile image : images) {
    		if (image.getOriginalFilename().equals(mainImgFileName)) {
    			mainImg = image;
    			log.info("mainImg : " + mainImg);
    			break;
    		}
    	}
    	
    	List<MultipartFile> imgList = new ArrayList<>(images);
    	
//    	imgList.remove(mainImg);	// 중복된 mainImg 리스트에서 삭제
    	
//    	imgList.add(0, mainImg);	// 다시 mainImg 를 배열 0번째 자리에 추가
    	
    	int boardNo = service.insertBoard(board, imgList);
    	
    	log.info("imgList : " + imgList);
    	
    	
    	if(boardNo > 0) {
  		
    		// 후에 리스트, 상세페이지 다 되면 상세페이지로 넘어가게 수정
    		path= "detail/" + boardNo;
    		message = "집들이 게시글 등록이 완료되었습니다.";
    		
    		
    	} else {
    		
    		path = "main";
    		message = "집들이 게시글 등록에 실패하였습니다.";
    		
    	}
	    

	    	
	    
	    ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	
	@DeleteMapping("deleteMyHouse")
	@ResponseBody
	public int deleteMyHouse(@RequestParam("boardNo") int boardNo,
							 @RequestParam("boardCode") int boardCode,
							 RedirectAttributes ra) {
		
		log.info("boardCode : " + boardCode);

		return service.deleteMyHouse(boardNo);
	}
	
	@GetMapping("updateMyHouse")
	public String updateMyHouse(@RequestParam("boardNo") int boardNo,
								@SessionAttribute(value="loginMember", required=false) Member loginMember,
								RedirectAttributes ra,
								Model model) {
		
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("boardNo", boardNo);
		
		Board board = service.selectBoard(map);
		
		if(loginMember == null || loginMember.getMemberNo() != board.getMemberNo()) {
			ra.addFlashAttribute("message", "접근 권한이 없습니다.");
			return "redirect:detail/" + boardNo;
		}
		
		log.info("boardNo : " + board.getBoardNo());
		
		model.addAttribute("board", board);
		
		return "board/myHouseBoard/updateMyHouse";
	}
	
	
	@PostMapping("updateMyHouse")
	public String updateMyHouse(@RequestParam("boardNo") int boardNo,
								@RequestParam("boardTitle") String inputBoardTitle,
								@RequestParam("boardContent") String inputBoardContent,
								@RequestParam("images") List<MultipartFile> images,
								HttpServletRequest req,
								@SessionAttribute("loginMember") Member loginMember,
								RedirectAttributes ra) throws IllegalStateException, IOException {
		
		log.info("boardNo : " + boardNo);
		
		Board board = new Board();
		
		board.setBoardNo(boardNo);
		board.setBoardTitle(inputBoardTitle);
		board.setBoardContent(inputBoardContent);
		
		int result = service.updateMyHouse(board, images);
		String path = null;
		String message = null;
		
		if(result > 0) {
			path = "detail/" + boardNo;
			message = "집들이 게시글 수정이 완료되었습니다.";
		} else {
			path = "updateMyHouse";
			message = "집들이 게시글 수정에 실패하였습니다.";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
		
}























