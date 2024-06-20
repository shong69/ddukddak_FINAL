package com.ddukddak.common.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.common.mapper.CommonMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class CommonServiceImpl implements CommonService {

	private final CommonMapper mapper;
	private final BCryptPasswordEncoder bcrypt;
	
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


	/** 비밀번호 재설정
	 *
	 */
	@Override
	public int changePw(Map<String, String> map) {
		
		String inputId = map.get("inputId");
		String inputTel = map.get("inputTel");
		String inputPw = map.get("newPw");
		
		String encPw = bcrypt.encode(inputPw);
	
		// 기존 비밀번호와 동일한지 여부 확인하기 위해 기존 비번 꺼내옴
		String oldPw = mapper.pwCheck(inputId);
		
		if(bcrypt.matches(inputPw, oldPw)) {
			
			return -1; // 기존 비번과 동일할 경우
		}
		
		Map<String, String> changeMap = new HashMap<>();
		
		changeMap.put("inputId", inputId);
		changeMap.put("inputTel", inputTel);
		changeMap.put("inputPw", encPw);
		
		int result = mapper.changeMemberPw(changeMap);
		
		if(result == 0) {
			
			result = mapper.changePartnerPw(changeMap);
		}
		
		return result;
		
	}

}
