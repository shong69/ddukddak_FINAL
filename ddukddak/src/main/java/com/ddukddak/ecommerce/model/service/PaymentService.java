package com.ddukddak.ecommerce.model.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface PaymentService {

	/** 토큰 얻기
	 * @return
	 * @throws Exception 
	 */
	String getAccessToken() throws Exception;

	/** 사전 검증
	 * @param params
	 * @param accessToken
	 * @return
	 */
	Map<String, Object> preparePayment(Map<String, Object> params, String accessToken);


}
