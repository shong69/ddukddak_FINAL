package com.ddukddak.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ddukddak.board.model.dto.Comment;
import com.ddukddak.board.model.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService service;
	
	
	@GetMapping("")
	public List<Comment> select(@RequestParam("boardNo") int boardNo) {
		
		// HttpMessageConverter 가 List -> JSON(문자열)로 변환해서 response
		
		return service.select(boardNo);
	}
}
