package com.ddukddak.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.service.MyHouseBoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("myHouse")
@RequiredArgsConstructor
@Slf4j
public class MyHouseBoardController {
	
	private final MyHouseBoardService service;

	@GetMapping("main")
	public String myHouseMain() {
		return "board/myHouseBoard/myHouseBoard";
	}
	
	@GetMapping("detail")
	public String myHouseDetail() {
		return "board/myHouseBoard/myHouseBoardDetail";
	}
	
	@GetMapping("registMyHouse")
	public String registMyHouse() {
		return "board/myHouseBoard/registMyHouse";
	}
	
	// 집들이 게시글 등록
	@PostMapping("registMyHouse")
	public String registMyHouse(@RequestParam ("boardTitle") String inputTitle,
								@RequestParam ("boardContent") String inputContent,
								@RequestParam ("mainImg") String mainImgFileName,
								@RequestParam ("images") List<MultipartFile> images,
								RedirectAttributes ra) throws IOException {
		
		
		Board board = new Board();
		
	    board.setBoardTitle(inputTitle);
	    board.setBoardContent(inputContent);
	    
	    
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
	    	
	    	path= "/myHouse/main";
	    	
	    	
	    } else {
	    	
	    	path = "/myHouse/registMyHouse";
	    
	    }
	    
		
		return "redirect:" + path;
	}
	
}























