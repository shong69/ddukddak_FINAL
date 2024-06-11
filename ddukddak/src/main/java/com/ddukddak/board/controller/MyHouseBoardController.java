package com.ddukddak.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.service.MyHouseBoardService;
import com.ddukddak.member.model.dto.Member;

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
							  Model model,
							  @RequestParam Map<String, Object> paramMap) {
		
		Map<String, Object> map = null;
		
		if(paramMap.get("query") == null) {
			
			map = service.selectMyHouseList(boardCode, cp);
			
		} else {
			log.info("boardCode : " + boardCode);
			
			paramMap.put("boardCode", boardCode);
			
			map = service.searchList(paramMap, cp);
			
		}
		
//		List<Board> myHouseList = service.selectMyHouseList(boardType);			
		
		model.addAttribute("boardCode", boardCode);
		model.addAttribute("myHouseList", map.get("myHouseList"));
		
		return "board/myHouseBoard/myHouseBoard";
	}
	
	@GetMapping("detail/{boardNo:[0-9]+}")
	public String myHouseDetail(@PathVariable("boardNo") int boardNo,
								Model model,
								RedirectAttributes ra,
								HttpServletRequest req,
								HttpServletResponse resp) {
		
		return "board/myHouseBoard/myHouseBoardDetail";
	}
	
	@GetMapping("registMyHouse")
	public String registMyHouse() {
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
	    
	    
	    log.info("images : " + images);

	    MultipartFile mainImg = null;
	    
	    for (MultipartFile image : images) {
	        if (image.getOriginalFilename().equals(mainImgFileName)) {
	            mainImg = image;
	            log.info("mainImg : " + mainImg);
	            break;
	        }
	    }
	    
	    List<MultipartFile> imgList = new ArrayList<>(images);
	    
	    imgList.remove(mainImg);
	    
	    imgList.add(0, mainImg);
	    
	    int boardNo = service.insertBoard(board, imgList);
	    
	    log.info("imgList : " + imgList);
	    
	    String path = null;
	 
	    if(boardNo > 0) {
	    	
	    	// 후에 리스트, 상세페이지 다 되면 상세페이지로 넘어가게 수정
	    	path= "/myHouse/detail/" + boardNo;
	    	
	    	
	    } else {
	    	
	    	path = "/myHouse/registMyHouse";
	    
	    }
	    
		
		return "redirect:" + path;
	}
	
}























