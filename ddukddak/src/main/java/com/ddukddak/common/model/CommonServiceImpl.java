package com.ddukddak.common.model;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.common.mapper.CommonMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

	private final CommonMapper mapper;
	
	
	/** 비밀번호 찾기 - 아이디 검색(공통)
	 *
	 */
	@Override
	public int idCheck(String id) {
		return mapper.idCheck(id);
	}


	/** 비밀번호 찾기 - 아이디, 전화번호 일치 여부 확인
	 *
	 */
	@Override
	public String commonITCheck(Map<String, String> map) {
		return mapper.commonITCheck(map);
	}

}
