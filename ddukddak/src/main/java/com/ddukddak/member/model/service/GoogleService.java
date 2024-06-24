package com.ddukddak.member.model.service;

import java.util.Map;

public interface GoogleService {

	/** 로그인 유저 정보 구하기
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	Map<String, Object> getGoogleUserInfo(String code) throws Exception;

}
