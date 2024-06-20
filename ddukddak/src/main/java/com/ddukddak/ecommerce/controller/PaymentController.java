package com.ddukddak.ecommerce.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddukddak.ecommerce.model.dto.Orders;
import com.ddukddak.ecommerce.model.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("payment")
public class PaymentController {
	
	private final PaymentService paymentService;
	
	@PostMapping("prepare")
	public Map<String, String> preparePayment(@RequestBody Map<String, Object> params) {
		log.info("merchantUid, myAmount 값 확인 : " + params);
				
		// 1. 토큰 얻기
		String accessToken =  paymentService.getAccessToken();
		
		log.info("AccessToken : " + accessToken);
		// 9f1e87f6e789121f789ab6e443650040f70090a1  잘 오는거 확인

		// 2. 사전 검증
		Map<String, Object> prepareResponse = paymentService.preparePayment(params, accessToken);		
		
		log.info("prepareResponse : " + prepareResponse);
		// prepareResponse : {amount=219900, merchant_uid=ORD-202406200334000001}
		
	    Map<String, String> response = new HashMap<>();
	    
	    // 사전 검증이 정상적으로 됐다면
	    if(prepareResponse != null) {
	    	
	    	// 3. 주문 테이블 생성
	    	Orders order = new Orders();
	    	
	    	// 테이블 세팅
	    	
		    // 4. 주문 데이터와 클라값 비교
	        
	    	// 여기서 부터 ㄱ

	        	
	    }
	   
	    

	
        
         
        	
        
        
		
		return response;
	}

}


