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

	/** 회원 관리 - 회원목록 조회
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectMember(int cp);

	/** 회원 탈퇴 처리
	 * @param memberNo
	 * @return
	 */
	int memberDelete(String memberNo);

	/** 회원 다중 탈퇴 처리
	 * @param action
	 * @param partners
	 * @return
	 */
	int memberMultiDelete(String action, List<Map<String, String>> members);

	/** 파트너 관리 = 파트너 목록 조회
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectPartner(int cp);

	/** 파트너 탈퇴 처리
	 * @param partnerNo
	 * @return
	 */
	int partnerDelete(String partnerNo);

	/** 파트너 다중 탈퇴 처리
	 * @param action
	 * @param partners
	 * @return
	 */
	int partnerMultiDelete(String action, List<Map<String, String>> partners);

	
}
