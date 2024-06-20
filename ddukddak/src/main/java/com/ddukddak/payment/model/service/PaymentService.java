package com.ddukddak.payment.model.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface PaymentService {

	/**
	 * 토큰 얻기
	 * @throws Exception 
	 */
	String getAccessToken(String impKey, String impSecret) throws Exception;

	/** 사전 검증
	 * @param params
	 * @param accessToken
	 * @return
	 */
	Map<String, Object> preparePayment(Map<String, Object> params, String accessToken);

	/** 사후 검증
	 * @param params
	 * @param accessToken
	 * @return
	 * @throws Exception 
	 */
	int verifyPayment(Map<String, Object> params, String accessToken) throws Exception;




}
