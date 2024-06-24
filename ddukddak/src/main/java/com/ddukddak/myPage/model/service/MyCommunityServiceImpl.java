package com.ddukddak.myPage.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.ecommerce.model.dto.eCommercePagination;
import com.ddukddak.main.model.dto.Pagination;
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
	public Map<String, Object> selectMyHouseBoardList(Member loginMember, int cp) {
		Map<String, Object> map = new HashMap<>();
		int memberNo = loginMember.getMemberNo();
		//1. 전체 게시글 조회
		int boardCount = mapper.selectMyHouseCount(memberNo);
		//2. pagination 객체 생성하기
		Pagination pagination = new Pagination(cp,boardCount);
		//3.페이지 목록 조회
		int limit = pagination.getLimit();
		int offset = (cp-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board>myHouseBoardList = mapper.selectMyHouseBoardList(memberNo, rowBounds);

		map.put("pagination", pagination);
		map.put("myHouseBoardList", myHouseBoardList);
		return map;
	}

	@Override
	public Map<String, Object> selectMyTipBoardList(Member loginMember, int cp) {
		Map<String, Object> map = new HashMap<>();
		int memberNo = loginMember.getMemberNo();
		int boardCount = mapper.selectMyTipCount(memberNo);
		//2. pagination 객체 생성하기
		eCommercePagination pagination = new eCommercePagination(cp,boardCount);
		//3.페이지 목록 조회
		int limit = pagination.getLimit();
		int offset = (cp-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board>myTipBoardList = mapper.selectMyTipBoardList(memberNo, rowBounds);
		
		map.put("pagination", pagination);
		map.put("myTipBoardList", myTipBoardList);
		
		return map;
	}

	@Override
	public Map<String, Object> selectLikeHouseBoardList(Member loginMember, int cp) {
		int memberNo = loginMember.getMemberNo();
		int boardCount = mapper.selectLikeHouseCount(memberNo);
		Map<String, Object> map = new HashMap<>();
		
		//2. pagination 객체 생성하기
		Pagination pagination = new Pagination(cp,boardCount);
		//3.페이지 목록 조회
		int limit = pagination.getLimit();
		int offset = (cp-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		
		List<Board> likeHouseBoardList = mapper.likeHouseBoardList(memberNo, rowBounds);
		
		map.put("pagination", pagination);
		map.put("likeHouseBoardList", likeHouseBoardList);
		return map;
	}

	@Override
	public Map<String, Object> selectLikeTipBoardList(Member loginMember, int cp) {
		int memberNo =loginMember.getMemberNo();
		int boardCount = mapper.selectLikeTipCount(memberNo);
		
		//2. pagination 객체 생성하기
		eCommercePagination pagination = new eCommercePagination(cp,boardCount);
		//3.페이지 목록 조회
		int limit = pagination.getLimit();
		int offset = (cp-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		Map<String, Object> map = new HashMap<>();
		List<Board> likeTipBoardList = mapper.likeTipBoardList(memberNo, rowBounds);
		map.put("pagination", pagination);
		map.put("likeTipBoardList", likeTipBoardList);
		return map;
	}
	

}
