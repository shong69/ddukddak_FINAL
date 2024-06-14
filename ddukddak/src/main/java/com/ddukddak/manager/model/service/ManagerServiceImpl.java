package com.ddukddak.manager.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.main.model.dto.Pagination;
import com.ddukddak.manager.model.mapper.ManagerMapper;
import com.ddukddak.partner.model.dto.Partner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerServiceImpl implements ManagerService {

	private final ManagerMapper mapper;
	/** 파트너 가입 승인 관리 - 목록 조회
	 * 
	 */
	@Override
	public Map<String, Object> selectPassList(int cp) {
		
		// 가입 대기 중인 파트너 수 조회
		int listCount = mapper.getListCount();
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Partner> passList = mapper.selectPassList(rowBounds);
		
		// 목록 + 페이지네이션
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("passList", passList);
		
		return map;
	}
	
	
	/** 파트너 가입 승인
	 *
	 */
	@Override
	public int passConfirm(String partnerNo) {
		
		
		// 파트너 가입 승인부터 승인
		int updateConfrim = mapper.updateConfrim(partnerNo);
		
	
		
		return updateConfrim;
	}
	
}
