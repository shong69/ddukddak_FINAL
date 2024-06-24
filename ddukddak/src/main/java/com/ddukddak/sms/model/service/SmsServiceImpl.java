package com.ddukddak.sms.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ddukddak.common.util.Utility;
import com.ddukddak.sms.model.mapper.SmsMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
@Slf4j
@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {
	
	private final DefaultMessageService messageService;

	private final SmsMapper mapper;

    @Value("${coolsms.from.number}")
    private String fromNumber;

	@Override
	public SingleMessageSentResponse sendSms(String toNumber) {
		
		// 응답 값 생성
		SingleMessageSentResponse response;
		
		// 난수 6자리 숫자 생성
		String smsAuthKey = Utility.RandomNumber6();
		
		log.info("list : " + smsAuthKey);
		
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
			return null;
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
		if(result == 0) {
			return null;
		} 
			
			
		
		return response;
		
	}


	/** 인증키 일치 여부 확인
	 *
	 */
	@Override
	public int checkSmsAuthKey(Map<String, Object> map) {
		
		return mapper.checkSmsAuthKey(map);
	}


	/** 파트너 가입 여부 SMS 발송
	 *
	 */
	@Override
	public SingleMessageSentResponse sendPartnerSms(String passStatus, String toNumber) {
		
		// 응답 값 생성
		SingleMessageSentResponse response;
		
		String msg = null;
		
		switch(passStatus) {
		
			case "confirm" : msg = "[뚝딱뚝딱] 귀하의 파트너 등록 요청이 승인되었습니다. 환영합니다 :)"; break;
			case "refuse" : msg = "[뚝딱뚝딱] 죄송합니다. 귀하의 파트너 등록 요청이 거절되었습니다."; break;
	
		}
		
		try {
			
			Message message = new Message();
			// 발신번호 및 수신번호는 반드시 01012345678 형태로 입력
			
			message.setFrom(fromNumber);
			message.setTo(toNumber);
			message.setText(msg);
			// 가입 여부에 따라 발송 메시지 바꿔주기
			
			response = this.messageService.sendOne(new SingleMessageSendingRequest(message)); // 실제 발송 구문
			
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		// 따로 결과만 발송이기 때문에 인증키 로직 없음
		
		return response;
			
	}

	// 파트너 다수 SMS 발송
	@Override
	public MultipleDetailMessageSentResponse sendPartnerManySms(String action, List<Map<String, String>> partners) {
		
		// 응답 값 생성
		MultipleDetailMessageSentResponse multiResponse;
		
		String msg = null;
		
		log.info("SMS 서비스단 : partners : " + partners );
		log.info("SMS 서비스단 : action : " + action);
		
		switch(action) {
		
			case "confirm" : msg = "[뚝딱뚝딱] 귀하의 파트너 등록 요청이 승인되었습니다. 환영합니다 :)"; break;
			case "refuse" : msg = "[뚝딱뚝딱] 죄송합니다. 귀하의 파트너 등록 요청이 거절되었습니다."; break;
	
		}
		
		ArrayList<Message> messageList = new ArrayList<>();
		
        for (int i = 0; i < partners.size(); i++) {
        	
            Map<String, String> partner = partners.get(i);
            String toNumber = partner.get("partnerTel");
        	
            Message message = new Message();
            // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
            message.setFrom(fromNumber);
            message.setTo(toNumber);
            message.setText(msg);

            // 메시지 건건 마다 사용자가 원하는 커스텀 값(특정 주문/결제 건의 ID를 넣는등)을 map 형태로 기입하여 전송 후 확인해볼 수 있습니다!
            /*HashMap<String, String> map = new HashMap<>();

            map.put("키 입력", "값 입력");
            message.setCustomFields(map);*/

            messageList.add(message);
        }
        
        try {
        	
            // send 메소드로 단일 Message 객체를 넣어도 동작합니다!
            // 세 번째 파라미터인 showMessageList 값을 true로 설정할 경우 MultipleDetailMessageSentResponse에서 MessageList를 리턴하게 됩니다!
            //MultipleDetailMessageSentResponse response = this.messageService.send(messageList, false, true);

            // 중복 수신번호를 허용하고 싶으실 경우 위 코드 대신 아래코드로 대체해 사용해보세요!
        	multiResponse = this.messageService.send(messageList, true);

            System.out.println(multiResponse);

            return multiResponse;
            
        } catch (NurigoMessageNotReceivedException exception) {
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
		
		
		return null;
	}

	
	// ---------------------------------------------------------------------------
	

	// 회원 탈퇴 메세지 전송
	@Override
	public SingleMessageSentResponse sendMemberSms(String delMessage, String toNumber) {
		/// 응답 값 생성
		SingleMessageSentResponse response;
		
		String msg = null;
		
		switch(delMessage) {
		
			case "delete" : msg = "[뚝딱뚝딱] 귀하의 회원정보가 탈퇴처리되었습니다. 죄송합니다 :("; break;
	
		}
		
		try {
			
			Message message = new Message();
			// 발신번호 및 수신번호는 반드시 01012345678 형태로 입력
			
			log.info("toNumber : " + toNumber);
			
			message.setFrom(fromNumber);
			message.setTo(toNumber);
			message.setText(msg);
			// 가입 여부에 따라 발송 메시지 바꿔주기
			
			response = this.messageService.sendOne(new SingleMessageSendingRequest(message)); // 실제 발송 구문
			
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		// 따로 결과만 발송이기 때문에 인증키 로직 없음
		
		return response;
	}
	

	// 회원 다수 탈퇴 SMS 발송
	@Override
	public MultipleDetailMessageSentResponse sendMemberMultiSms(String action, List<Map<String, String>> members) {
		// 응답 값 생성
		MultipleDetailMessageSentResponse multiResponse;
		
		String msg = null;

		
		switch(action) {
		
			case "delete" : msg = "[뚝딱뚝딱] 귀하의 회원정보가 탈퇴처리되었습니다. 죄송합니다 :("; break;
	
		}
		
		ArrayList<Message> messageList = new ArrayList<>();
		
        for (int i = 0; i < members.size(); i++) {
        	
            Map<String, String> member = members.get(i);
            String toNumber = member.get("memberTel");
        	
            Message message = new Message();
            // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
            message.setFrom(fromNumber);
            message.setTo(toNumber);
            message.setText(msg);

            // 메시지 건건 마다 사용자가 원하는 커스텀 값(특정 주문/결제 건의 ID를 넣는등)을 map 형태로 기입하여 전송 후 확인해볼 수 있습니다!
            /*HashMap<String, String> map = new HashMap<>();

            map.put("키 입력", "값 입력");
            message.setCustomFields(map);*/

            messageList.add(message);
        }
        
        try {
        	
            // send 메소드로 단일 Message 객체를 넣어도 동작합니다!
            // 세 번째 파라미터인 showMessageList 값을 true로 설정할 경우 MultipleDetailMessageSentResponse에서 MessageList를 리턴하게 됩니다!
            //MultipleDetailMessageSentResponse response = this.messageService.send(messageList, false, true);

            // 중복 수신번호를 허용하고 싶으실 경우 위 코드 대신 아래코드로 대체해 사용해보세요!
        	multiResponse = this.messageService.send(messageList, true);

            System.out.println(multiResponse);

            return multiResponse;
            
        } catch (NurigoMessageNotReceivedException exception) {
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
		
		
		return null;
	}


	// 파트너 탈퇴 SMS 발송
	@Override
	public SingleMessageSentResponse sendPartnerSms2(String string, String toNumber) {
		/// 응답 값 생성
				SingleMessageSentResponse response;
				
				String msg = null;
				
				switch(string) {
				
					case "delete" : msg = "[뚝딱뚝딱] 귀하의 파트너정보가 탈퇴처리되었습니다. 죄송합니다 :("; break;
			
				}
				
				try {
					
					Message message = new Message();
					// 발신번호 및 수신번호는 반드시 01012345678 형태로 입력
					
					log.info("toNumber : " + toNumber);
					
					message.setFrom(fromNumber);
					message.setTo(toNumber);
					message.setText(msg);
					// 가입 여부에 따라 발송 메시지 바꿔주기
					
					response = this.messageService.sendOne(new SingleMessageSendingRequest(message)); // 실제 발송 구문
					
					
				} catch(Exception e) {
					e.printStackTrace();
					return null;
				}
				
				// 따로 결과만 발송이기 때문에 인증키 로직 없음
				
				return response;
	}

	
	// 파트너 탈퇴 다수 문자 발송
	@Override
	public MultipleDetailMessageSentResponse sendPartnerMultiSms(String action, List<Map<String, String>> partners) {
		
		// 응답 값 생성
		MultipleDetailMessageSentResponse multiResponse;
		
		String msg = null;

		
		switch(action) {
		
			case "delete" : msg = "[뚝딱뚝딱] 귀하의 파트너정보가 탈퇴처리되었습니다. 죄송합니다 :("; break;
	
		}
		
		ArrayList<Message> messageList = new ArrayList<>();
		
        for (int i = 0; i < partners.size(); i++) {
        	
            Map<String, String> member = partners.get(i);
            String toNumber = member.get("partnerTel");
        	
            Message message = new Message();
            // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
            message.setFrom(fromNumber);
            message.setTo(toNumber);
            message.setText(msg);

            // 메시지 건건 마다 사용자가 원하는 커스텀 값(특정 주문/결제 건의 ID를 넣는등)을 map 형태로 기입하여 전송 후 확인해볼 수 있습니다!
            /*HashMap<String, String> map = new HashMap<>();

            map.put("키 입력", "값 입력");
            message.setCustomFields(map);*/

            messageList.add(message);
        }
        
        try {
        	
            // send 메소드로 단일 Message 객체를 넣어도 동작합니다!
            // 세 번째 파라미터인 showMessageList 값을 true로 설정할 경우 MultipleDetailMessageSentResponse에서 MessageList를 리턴하게 됩니다!
            //MultipleDetailMessageSentResponse response = this.messageService.send(messageList, false, true);

            // 중복 수신번호를 허용하고 싶으실 경우 위 코드 대신 아래코드로 대체해 사용해보세요!
        	multiResponse = this.messageService.send(messageList, true);

            System.out.println(multiResponse);

            return multiResponse;
            
        } catch (NurigoMessageNotReceivedException exception) {
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
		
		
		return null;
	}

    
    


	
}
