package com.ddukddak.member.model.service;

import java.util.Map;

public interface NaverService {

	/** 네이버 유저 정보 얻어오기(토큰 안에서 작업)
	 * @param code
	 * @param state
	 * @return
	 */
	Map<String, Object> getNaverUserInfo(String code, String state);

}
