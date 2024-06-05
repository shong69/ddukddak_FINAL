package com.ddukddak.email.model.service;

import java.util.Map;

public interface EmailService {

	/** 이메일 발송
	 * @param string
	 * @param email
	 * @return
	 */
	String sendEmail(String pageName, String email);

	/** 인증키 일치 여부 확인
	 * @param map
	 * @return
	 */
	int checkAuthKey(Map<String, Object> map);

	
	void sendEmailAsync(String pageName, String email);

}
