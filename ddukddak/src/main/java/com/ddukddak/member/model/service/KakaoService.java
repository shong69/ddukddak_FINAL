package com.ddukddak.member.model.service;

import java.util.Map;

public interface KakaoService {

	/** 토큰 얻기
	 * @param code
	 * @return
	 */
	String getAccessToken(String code);

	/** 사용자 정보 얻기
	 * @param accessToken
	 * @return
	 */
	Map<String, Object> getUserInfo(String accessToken);

}
