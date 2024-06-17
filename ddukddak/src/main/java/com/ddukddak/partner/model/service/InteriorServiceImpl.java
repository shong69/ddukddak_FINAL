package com.ddukddak.partner.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.mapper.InteriorMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InteriorServiceImpl implements InteriorService {
	
	private final InteriorMapper mapper;
	
	
	// 시공사 리스트 조회
	@Override
	public List<Partner> selectInteriorList() {
		
		return mapper.selectIneriorList();
	}

}
