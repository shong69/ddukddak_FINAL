package com.ddukddak.common.model;

import java.util.Map;

public interface CommonService {

	/** 비밀번호 찾기 - 아이디 검색(공통)
	 * @param id
	 * @return
	 */
	int idCheck(String id);

	/** 비밀번호 찾기 - 아이디, 전화번호 일치 여부 확인
	 * @param map
	 * @return
	 */
	String commonITCheck(Map<String, String> map);

	/** 비밀번호 재설정
	 * @param map
	 * @return
	 */
	int changePw(Map<String, String> map);

}
