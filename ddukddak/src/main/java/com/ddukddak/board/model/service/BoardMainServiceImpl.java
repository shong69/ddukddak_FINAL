package com.ddukddak.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.board.model.mapper.BoardMainMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@PropertySource("classpath:config.properties")
public class BoardMainServiceImpl implements BoardMainService{

	private final BoardMainMapper mapper;
	
//	@Value("${my.board.web-path}")
//	private String webPath;

	@Override
	public List<Map<String, Object>> selectBoardTypeList() {
		return mapper.selectBoardTypeList();
	} 
	
//	@Value("${my.board.folder-path}")
//	private String folderPath;
	
	
}
