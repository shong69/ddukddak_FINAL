package com.ddukddak.myPage.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.myPage.model.mapper.myBoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class MyCommunityServiceImpl implements MyCommunityService{
	private final myBoardMapper mapper;

	//내 집들이 게시글 진입(조회)
	@Override
	public Map<String, Object> selectMyHouseBoardList(Member loginMember) {
		Map<String, Object> map = new HashMap<>();
		int memberNo = loginMember.getMemberNo();
		
		List<Board>myHouseBoardList = mapper.selectMyHouseBoardList(memberNo);

		map.put("myHouseBoardList", myHouseBoardList);
		return map;
	}

	@Override
	public Map<String, Object> selectMyTipBoardList(Member loginMember) {
		Map<String, Object> map = new HashMap<>();
		int memberNo = loginMember.getMemberNo();
		
		List<Board>myTipBoardList = mapper.selectMyTipBoardList(memberNo);

		map.put("myTipBoardList", myTipBoardList);
		
		return map;
	}
	

}
