package com.ddukddak.myPage.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ddukddak.member.model.dto.Member;
import com.ddukddak.myPage.model.service.MyCommunityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("myCommunity")
@Controller
@RequiredArgsConstructor
public class MyCommunityController {
	private final MyCommunityService service;

	/** 내 집들이 게시글 진입(조회)
	 * @param loginMember
	 * @param model
	 * @return
	 */
	@GetMapping("myPageHouseBoard")
	public String myPageHouseBoard(
			@SessionAttribute("loginMember")Member loginMember,
			Model model) {
		Map<String, Object> map = service.selectMyHouseBoardList(loginMember);
		model.addAttribute("myHouseBoardList", map.get("myHouseBoardList"));
		
		return "myPage/myPageHouseBoard";
	}
	
	
	//내가 쓴 노하우 게시글
	@GetMapping("myPageTipBoard")
	public String myPageTipBoard(
			@SessionAttribute("loginMember")Member loginMember,
			Model model) {
		
		Map<String, Object> map = service.selectMyTipBoardList(loginMember);
		model.addAttribute("myTipBoardList", map.get("myTipBoardList"));
		
		return "myPage/myPageTipBoard";
	}
	
	//내가 좋아요 한 게시글
	@GetMapping("myLikes")
	public String myLikes(@SessionAttribute("loginMember")Member loginMember,
			Model model) {
		
		Map<String, Object> map = service.selectMyHouseBoardList(loginMember);
		model.addAttribute("myHouseBoardList", map.get("myHouseBoardList"));
		return "myPage/myLikes";
	}
	
	
	@ResponseBody
	@GetMapping("House")
	public Object likeHouses(@SessionAttribute("loginMember")Member loginMember,
			Model model) {
		
		Map<String, Object> map = service.selectLikeHouseBoardList(loginMember);

		return map.get("likeHouseBoardList");
	}
	
	@ResponseBody
	@GetMapping("Tip")
	public Object likeTips(@SessionAttribute("loginMember")Member loginMember,
			Model model) {
		Map<String, Object> map = service.selectLikeTipBoardList(loginMember);

		return map.get("likeTipBoardList");
	}
	

}
