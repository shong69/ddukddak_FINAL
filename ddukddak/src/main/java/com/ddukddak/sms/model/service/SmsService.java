package com.ddukddak.sms.model.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
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

	/** 파트너 가입 여부 SMS 발송
	 * @param string
	 * @param toNumber
	 * @return
	 */
	SingleMessageSentResponse sendPartnerSms(String passStatus, String toNumber);

	/** 파트너 다수 SMS 발송 - 가입
	 * @param action
	 * @param partners
	 * @return
	 */
	MultipleDetailMessageSentResponse sendPartnerManySms(String action, List<Map<String, String>> partners);
	
	/** 회원 탈퇴 SMS 발송
	 * @param string
	 * @param toNumber
	 * @return
	 */
	SingleMessageSentResponse sendMemberSms(String delMessage, String toNumber);	

	/** 회원 다수 탈퇴 SMS 발송
	 * @param action
	 * @param partners
	 * @return
	 */
	MultipleDetailMessageSentResponse sendMemberMultiSms(String action, List<Map<String, String>> partners);

	/** 파트너 탈퇴 SMS 발송
	 * @param string
	 * @param toNumber
	 * @return
	 */
	SingleMessageSentResponse sendPartnerSms2(String string, String toNumber);

	/** 파트너 탈퇴 SMS 발송
	 * @param action
	 * @param partners
	 * @return
	 */
	MultipleDetailMessageSentResponse sendPartnerMultiSms(String action, List<Map<String, String>> partners);


	
	
}
