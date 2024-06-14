package com.ddukddak.manager.model.service;

import java.util.Map;

public interface ManagerService {

	/** 파트너 가입 승인 관리 - 목록 조회
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectPassList(int cp);

	int passConfirm(String partnerNo);

}
