package com.ddukddak.sms.model.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ddukddak.common.util.Utility;
import com.ddukddak.sms.model.mapper.SmsMapper;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {
	
	private final DefaultMessageService messageService;

	private final SmsMapper mapper;
	
	
    @Value("${coolsms.from.number}")
    private String fromNumber;

    @Async
	@Override
	public CompletableFuture<SingleMessageSentResponse> sendSms(String toNumber) {
		
		SingleMessageSentResponse response;
		String smsAuthKey = Utility.RandomNumber6();
		
		try {
			
			Message message = new Message();
			// 발신번호 및 수신번호는 반드시 01012345678 형태로 입력
			
			message.setFrom(fromNumber);
			message.setTo(toNumber);
			message.setText("[뚝딱뚝딱] 인증번호 [" + smsAuthKey + "]를 입력해 주세요.");
			
			response = this.messageService.sendOne(new SingleMessageSendingRequest(message)); // 실제 발송 구문
			

		    /* 리스폰즈 예시
		     respons : 
		         SingleMessageSentResponse(groupId=G4V2024060512250195ZTRCX1C8T75X5, 
		         to=01032920409, 
		         from=01032920409, 
		         type=SMS, 
		         statusMessage=정상 접수(이통사로 접수 예정) , 
		         country=82, messageId=M4V20240605122501GGPHRIPH5V8BKS0, 
		         statusCode=2000, 
		         accountId=24052703872352)
		     */
			
			
		} catch(Exception e) {
			e.printStackTrace();
			return CompletableFuture.completedFuture(null);
		}
		
		// 수신번호 + 인증번호 SMS_AUTH_KEY 테이블에 저장
		Map<String, String> map = new HashMap<>();
		
		
		map.put("smsAuthKey", smsAuthKey);
		map.put("smsTel", toNumber);
		
		// 업데이트 선 시도
		int result = mapper.updateSmsAuthKey(map);
		
		// 업데이트 실패 -> 삽입
		if(result == 0) {
			result = mapper.insertSmsAuthKey(map);
		}
		
		// 수정, 삽입 후에도 result 가 0 == 실패
		if(result == 0) return CompletableFuture.completedFuture(null);
		
		return CompletableFuture.completedFuture(response);
		
	}


	/** 인증키 일치 여부 확인
	 *
	 */
	@Override
	public int checkSmsAuthKey(Map<String, Object> map) {
		
		return mapper.checkSmsAuthKey(map);
	}
    
    


	
}
