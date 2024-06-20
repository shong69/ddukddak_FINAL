package com.ddukddak.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.dto.BoardImg;
import com.ddukddak.board.model.dto.Report;
import com.ddukddak.board.model.mapper.BoardMainMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@PropertySource("classpath:config.properties")
public class BoardMainServiceImpl implements BoardMainService{

	
	private final BoardMainMapper mapper;

	@Override
	public List<Map<String, Object>> selectBoardTypeList() {
		return mapper.selectBoardTypeList();
	}

	@Override
	public List<BoardImg> selectBoardMainAd() {

		return mapper.selectBoardMainAd();
	}

	@Override
	public List<Board> selectBoard() {
		return mapper.selectBoard();
	}

	// 좋아요 체크 / 해제
	@Override
	public int boardLike(Map<String, Integer> map) {
		
		int result = 0;
		
		if(map.get("likeCheck") == 1) {
			result = mapper.deleteBoardLike(map);
		} else {
			result = mapper.insertBoardLike(map);
		}
		
		if(result > 0) {
			return mapper.selectLikeCount(map.get("boardNo"));
		}
		
		return -1;
	}

	
	// 신고 등록
	@Override
	public int insertReport(Report report) {
		
		return mapper.insertReport(report);
	} 

	
	
}
