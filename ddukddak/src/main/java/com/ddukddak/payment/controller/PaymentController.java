package com.ddukddak.payment.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddukddak.common.config.PaymentConfig;
import com.ddukddak.ecommerce.model.dto.Orders;
import com.ddukddak.ecommerce.model.mapper.eCommerceMapper;
import com.ddukddak.ecommerce.model.service.eCommerceService;
import com.ddukddak.payment.model.dto.TokenDTO;
import com.ddukddak.payment.model.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("payment")
public class PaymentController {
	
	private final PaymentService paymentService;
	private final PaymentConfig paymentConfig;
	private final eCommerceMapper eCommerceMapper;
	private final eCommerceService eCommerceService;
	
	private TokenDTO tokenDTO;
	
	@PostMapping("createOrder")
	public int createOrder(@RequestBody Map<String, Object> params) throws Exception {
		
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
    	order.setStatus("prepare");
    	
    	log.info("order 객체 결과 : " + order);
    	
    	int result = eCommerceService.createOrder(order);
        
    	if(result > 0) {
    		
            String impKey = paymentConfig.getPayApikey();
            String impSecret = paymentConfig.getPaySecret();
    		
    		String accessToken = paymentService.getAccessToken(impKey, impSecret);
    		
    		tokenDTO = new TokenDTO();
    		
    		tokenDTO.setAccessToken(accessToken);
    		
    		
    	} 
    	
		return result;
	}
	
	
	@PostMapping("prepare")
	public Map<String, String> preparePayment(@RequestBody Map<String, Object> params) throws Exception {
		log.info("사전 검증 merchantUid, myAmount, memberNo 값 확인 : " + params);
		// 사전 검증 merchantUid, myAmount, memberNo 값 확인 : {merchant_uid=ORD-202406200943550003, amount=10, memberNo=5}		
		
		// 1. 토큰 얻기
		
		
		String accessToken = tokenDTO.getAccessToken();
		
		
		log.info("AccessToken : " + accessToken);
		// 9f1e87f6e789121f789ab6e443650040f70090a1  잘 오는거 확인

		// 2. 사전 검증
		Map<String, Object> prepareResponse = paymentService.preparePayment(params, accessToken);		
		
		log.info("prepareResponse : " + prepareResponse);
		// prepareResponse : {amount=179000, merchant_uid=ORD-202406201026550007}
		
	    Map<String, String> response = new HashMap<>();
	    
	    // 사전 검증 등록 실패 시 
        if (prepareResponse == null) {
            
        	response.put("merchantUid", (String) params.get("merchant_uid"));
        	response.put("status", "fail");
            response.put("message", "사전 검증 실패");
            
            eCommerceMapper.reasonUpdate(response);
            
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
        // status 변경 필요 merchantUid 참고해서
        int updateStatus = eCommerceService.readyUpdate(merchantUid);
        
        if(updateStatus > 0) { // status 업데이트 성공
        	
            response.put("status", "success");
            response.put("merchant_uid", merchantUid);
            response.put("amount", String.valueOf(amount));
            
        }
        
		return response;
	}
	
	
	@PostMapping("verify")
	public Map<String, String> verifyPayment(@RequestBody Map<String, Object> params) throws Exception {
		
		
		String accessToken = tokenDTO.getAccessToken();
		log.info("사후 검증 accessToken DTO : " +  accessToken);
		
		// 사후 검증 값 받아오기
		int verifyResponse = paymentService.verifyPayment(params, accessToken);
		
		// 반환값 세팅
		Map<String, String> response = new HashMap<>();
		
		if(verifyResponse == 0) {
			
            response.put("status", "fail");
            response.put("message", "verify payment failed.");
            
            return response;
		}
		
		// DB랑 비교하고 넘어왔기 때문에 기존 Orders 테이블 업데이트
		String merchantUid = (String) params.get("merchant_uid");
		
		int result = eCommerceService.paidUpdate(merchantUid);
		
		if(result == 0) {
            response.put("status", "fail");
            response.put("message", "paid 업데이트 실패");
            
            return response;
		}
		
		
		// 성공
		response.put("status", "success");
		
		
		return response;
		
		
	}
	
	
	/** 사용자 취소
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping("cancel")
	public int cancelPayment(@RequestBody Map<String, Object> params) throws Exception {
	    String merchantUid = (String) params.get("merchant_uid");
	    String message = (String) params.get("message");
	    
	    Map<String, String> map = new HashMap<>();
	    
	    map.put("merchantUid", merchantUid);
	    map.put("message", message);
	    
	    // 결제 취소 상태 업데이트 로직
	    int result = eCommerceService.cancelUpdate(map);
	    
	    
	    
	    return result;
	}

}


