package com.ddukddak.payment.model.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.ddukddak.payment.model.dto.PaymentDTO;

public interface PaymentService {

	/**
	 * 토큰 얻기
	 * @param merchantUid 
	 * @throws Exception 
	 */
	String getAccessToken(String impKey, String impSecret, String merchantUid) throws Exception;

	/** 사전 검증
	 * @param params
	 * @param accessToken
	 * @return
	 * @throws Exception 
	 */
	Map<String, Object> preparePayment(Map<String, Object> params, String accessToken) throws Exception;

	/** 사후 검증
	 * @param params
	 * @param accessToken
	 * @return
	 * @throws Exception 
	 */
	int verifyPayment(Map<String, Object> params, String accessToken) throws Exception;

	/** 구매 완료 페이지 값 얻어오기
	 * @param merchantUid
	 * @return
	 */
	PaymentDTO selectPaid(String merchantUid);




}
