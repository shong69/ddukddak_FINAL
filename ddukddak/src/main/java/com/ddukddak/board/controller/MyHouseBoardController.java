package com.ddukddak.board.controller;

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

import com.ddukddak.board.model.dto.Board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("myHouse")
@RequiredArgsConstructor
@Slf4j
public class MyHouseBoardController {

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
								@RequestParam ("mainImg") MultipartFile mainImg,
								@RequestParam ("images") List<MultipartFile> images) {
		
		Board board = new Board();
		
		board.setBoardTitle(inputTitle);
		board.setBoardContent(inputContent);
		
		log.debug("board : " + board);
		
		List<MultipartFile> imgList = new ArrayList<>();
		
		imgList.add(mainImg);
		imgList.addAll(images);
		
		log.debug("imgList : " + imgList);
		
		return "redirect:/myHouse/main";
	}
	
}
