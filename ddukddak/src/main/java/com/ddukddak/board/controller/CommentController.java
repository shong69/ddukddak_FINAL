package com.ddukddak.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ddukddak.board.model.dto.Comment;
import com.ddukddak.board.model.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

	private final CommentService service;
	
	
	@GetMapping("")
	public List<Comment> select(@RequestParam("boardNo") int boardNo) {
		
		// HttpMessageConverter 가 List -> JSON(문자열)로 변환해서 response
		
		log.info("commentList : " + service.select(boardNo));
		
		return service.select(boardNo);
	};
	
	
	@PostMapping("listAndCount")
	public Map<String, Object> listAndCount(@RequestBody Comment comment) {
		
		int result = service.insert(comment);
		
	    Map<String, Object> response = new HashMap<>();
	    
	    if (result > 0) {
	    	
	        int count = service.getCommentCount(comment.getBoardNo());
	        response.put("success", true);
	        response.put("count", count);
	        
	    } else {
	    	
	        response.put("success", false);
	        
	    }
	    
	    return response;
	};
	
	@PostMapping("")
	public Map<String, Object> insert(@RequestBody Comment comment) {
		 Map<String, Object> response = new HashMap<>();
	        int result = service.insert(comment);

	        if (result > 0) {
	            int count = service.getCommentCount(comment.getBoardNo());
	            response.put("success", true);
	            response.put("count", count);
	        } else {
	            response.put("success", false);
	        }

	        return response;
	}
	
   @PutMapping("update")
    public Map<String, Object> update(@RequestBody Comment comment) {
        int result = service.update(comment);
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return response;
    }
   
   @DeleteMapping("delete")
   public Map<String, Object> delete(@RequestParam("commentNo") int commentNo) {
       int result = service.delete(commentNo);
       Map<String, Object> response = new HashMap<>();
       if (result > 0) {
           response.put("success", true);
       } else {
           response.put("success", false);
       }
       return response;
   }
	
}
