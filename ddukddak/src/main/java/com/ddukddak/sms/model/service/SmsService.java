package com.ddukddak.sms.model.service;

import java.util.Map;

import net.nurigo.sdk.message.response.SingleMessageSentResponse;

public interface SmsService {

	/** SMS 발송
	 * @param toNumber
	 * @return
	 */
	SingleMessageSentResponse sendSms(String toNumber);

	/** SMS 인증키 일치 여부 확인
	 * @param map
	 * @return
	 */
	int checkSmsAuthKey(Map<String, Object> map);
	
	
}
