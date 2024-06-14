package com.ddukddak.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.mapper.tipBoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class tipBoardServiceImpl implements tipBoardService{

	private final tipBoardMapper mapper;

	@Override
	public List<Board> selectTipList(int boardCode, String sort) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardCode",boardCode);
		map.put("sort",sort);
		
		List<Board> tipList = mapper.selectTipList(map);
		
		
		return tipList;
	}

	@Override
	public List<Board>  searchList(int boardCode, String sort, String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int tipLike(Map<String, Integer> map) {
		int result = 0;
		
		if (map.get("likeCheck") == 1) {
			result = mapper.tipLikeDelete(map);
		} else {
			result = mapper.tipLikeInsert(map);
		}
		
		if(result > 0) {
			return mapper.selectLikeCount(map.get("boardNo"));
		}
		
		return result;
	}
	
}
