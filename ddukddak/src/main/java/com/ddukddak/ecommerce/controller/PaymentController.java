package com.ddukddak.ecommerce.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddukddak.ecommerce.model.dto.Orders;
import com.ddukddak.ecommerce.model.service.PaymentService;
import com.ddukddak.ecommerce.model.service.eCommerceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("payment")
public class PaymentController {
	
	private final PaymentService paymentService;
	private final eCommerceService eCommerceService;
	
	
	@PostMapping("createOrder")
	public int createOrder(@RequestBody Map<String, Object> params) {
		
		log.info("주문 생성 merchantUid, myAmount, memberNo 값 확인 : " + params);
		//주문 생성 merchantUid, myAmount, memberNo 값 확인 : {merchant_uid=ORD-202406200943550003, amount=10, memberNo=5}
		
    	// 테이블에 넣을 값 가져오기
    	int memberNo = (int) params.get("memberNo");
    	log.info("memberNo : " + memberNo);
    	
    	String merchantUid = (String) params.get("merchant_uid");
    	log.info("merchantUid : " + merchantUid);
    	
    	int amount = (int) params.get("amount");
    	log.info("amount : " + amount);
		
   
    	// 주문 테이블 생성
    	Orders order = new Orders();
    	
    	order.setMemberNo(memberNo);
    	order.setTotalPrice(amount);
    	order.setMerchantUid(merchantUid);
    	order.setStatus("PREPARE");
    	
    	log.info("order 객체 결과 : " + order);
    	
    	int result = eCommerceService.createOrder(order);
        	
    	
		return result;
	}
	
	
	@PostMapping("prepare")
	public Map<String, String> preparePayment(@RequestBody Map<String, Object> params) throws Exception {
		log.info("사전 검증 merchantUid, myAmount, memberNo 값 확인 : " + params);
		// 사전 검증 merchantUid, myAmount, memberNo 값 확인 : {merchant_uid=ORD-202406200943550003, amount=10, memberNo=5}		
		
		// 1. 토큰 얻기
		String accessToken = paymentService.getAccessToken();
		
		log.info("AccessToken : " + accessToken);
		// 9f1e87f6e789121f789ab6e443650040f70090a1  잘 오는거 확인

		// 2. 사전 검증
		Map<String, Object> prepareResponse = paymentService.preparePayment(params, accessToken);		
		
		log.info("prepareResponse : " + prepareResponse);
		// prepareResponse : {amount=179000, merchant_uid=ORD-202406201026550007}
		
	    Map<String, String> response = new HashMap<>();
	    
	    // 사전 검증 등록 실패 시 
        if (prepareResponse == null) {
            response.put("status", "fail");
            response.put("message", "Prepare payment failed.");
            return response;
        }
	    
        // 3. DB와 비교
        String merchantUid = (String) prepareResponse.get("merchant_uid");
        int amount = (int) prepareResponse.get("amount");
	   
       
        Orders order = eCommerceService.prepareOrder(merchantUid);
	    
        if (order == null) {
            response.put("status", "fail");
            response.put("message", "Order not found.");
            return response;
        }
        
        int serverAmount = order.getTotalPrice();
        
        if (serverAmount != amount) {
            response.put("status", "fail");
            response.put("message", "Amount mismatch.");
            return response;
        }
        
        // 4. 사전 검증 성공
        response.put("status", "success");
        response.put("merchant_uid", merchantUid);
        response.put("amount", String.valueOf(amount));
	
		
		return response;
	}
	

}


