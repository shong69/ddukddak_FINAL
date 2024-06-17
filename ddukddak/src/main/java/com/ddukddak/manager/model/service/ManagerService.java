package com.ddukddak.manager.model.service;

import java.util.List;
import java.util.Map;

public interface ManagerService {

	/** 파트너 가입 승인 관리 - 목록 조회
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectPassList(int cp);

	/** 파트너 승인
	 * @param partnerNo
	 * @return
	 */
	int passConfirm(String partnerNo);

	/** 파트너 거절
	 * @param partnerNo
	 * @return
	 */
	int passRefuse(String partnerNo);

	/** 다중 업데이트
	 * @param status
	 * @param partners
	 * @return
	 */
	int updateMultiPass(String status, List<Map<String, String>> partners);

	
}
