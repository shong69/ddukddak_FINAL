package com.ddukddak.sms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ddukddak.sms.model.service.SmsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;


@RestController
@RequestMapping("sms")
@Slf4j
@RequiredArgsConstructor
public class SmsController {

    
    private final SmsService service;
    
    /** SMS 발송 - 단일 메시지
     * @param toNumber
     * @return
     */
    @PostMapping("/sendOne")
    public int sendOne(@RequestBody String toNumber) {
    
    
    	SingleMessageSentResponse response = service.sendSms(toNumber);
    	// response : SMS 발송 결과
    	
    	log.info("response : " + response);
    	
    	// 발송정보 != null == 성공
    	if(response != null) {
    		
    		return 1; // 성공
    		
    	}
    	
    	return 0; // 실패
    	

    }
    
    
    /** [myPage] 휴대폰 번호 변경:인증번호 발송
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("updatePhoneNum")
    public ResponseEntity<String> updatePhoneNum(@RequestBody Map<String, String> request){
    	String phoneNum = request.get("phoneNum");
    	
    	if(phoneNum== null || phoneNum.isEmpty()) {
    		return ResponseEntity.badRequest().build();
    	}
    	
    	SingleMessageSentResponse response = service.sendSms(phoneNum);
    	log.info("결과:"+ response);
    	if(response != null) return ResponseEntity.ok().build(); //비동기 여부에 대한 http응답 반환
    	else				 return ResponseEntity.badRequest().build();
    }
 
    
   
    
    
    /** SMS 인증키 일치 여부 확인
     * @param map
     * @return
     */
    @PostMapping("checkSmsAuthKey")
    public int checkSmsAuthKey(@RequestBody Map<String, Object> map) {
    	
    	// 일치 1, 불일치 0
    	return service.checkSmsAuthKey(map);
    }
    
    
    
    
    /** 파트너 승인 SMS 발송
     * @param toNumber
     * @return
     */
    @PostMapping("/sendOne/confirm")
    public int partnerConfirm(@RequestBody String toNumber) {
    	
    	
    	// 승인이냐, 거절이냐에 따라 문자 발송 양식 수정을 위해 키값 추가
    	SingleMessageSentResponse response = service.sendPartnerSms("confirm", toNumber);
    	
    	// response : SMS 발송 결과
    	log.info("response : " + response);
    	
    	// 발송정보 != null == 성공
    	if(response != null) {
    		
    		return 1; // 성공
    		
    	}
    	
    	return 0; // 실패
    }
    
    
    /** 파트너 거절 SMS 발송
     * @param toNumber
     * @return
     */
    @PostMapping("/sendOne/refuse")
    public int partnerRefuse(@RequestBody String toNumber) {
    	
    	
    	// 승인이냐, 거절이냐에 따라 문자 발송 양식 수정을 위해 키값 추가
    	SingleMessageSentResponse response = service.sendPartnerSms("refuse", toNumber);
    	
    	// response : SMS 발송 결과
    	log.info("response : " + response);
    	
    	// 발송정보 != null == 성공
    	if(response != null) {
    		
    		return 1; // 성공
    		
    	}
    	
    	return 0; // 실패
    }
    
    
    /** 파트너 다수 SMS 발송 - 가입
     * @param action
     * @param paramMap
     * @return
     */
    @PostMapping("/sendMany/mulit/{actionForBackend}")
    public  ResponseEntity<Integer> sendManyPartnerPass(@PathVariable("actionForBackend") String action, 
			@RequestBody Map<String, List<Map<String, String>>> paramMap) {
    	
    	
    	List<Map<String, String>> partners = paramMap.get("partners");
    	
		log.info("SMS 컨트롤러 partners: " + partners);
		log.info("SMS 컨트롤러 action: " + action);
		
        if (action == null || (!action.equals("confirm") && !action.equals("refuse"))) {
        	return ResponseEntity.ok(0);  // Invalid action
        }
    	
    	MultipleDetailMessageSentResponse multiResponse = service.sendPartnerManySms(action, partners);
    	
    	// response : SMS 발송 결과
    	log.info("multiResponse : " + multiResponse);
    	
    	/* 
    	 	MultipleDetailMessageSentResponse(
    	 	failedMessageList=[], 
    	 	groupInfo=MultipleMessageSentResponse(groupId=G4V20240616021623JA4BH7W3CJZCYTV, 
    	 	messageId=null, accountId=24052703872352, statusMessage=null, statusCode=null, 
    	 	to=null, from=null, type=null, country=null, 
    	 	count=Count(total=2, sentTotal=0, sentFailed=0, sentSuccess=0, 
    	 	sentPending=0, sentReplacement=0, refund=0, registeredFailed=0, 
    	 	registeredSuccess=2)), messageList=null)
    	
    	*/
    	// 성공 결과 회수 가져오기
    	int registeredSuccess = multiResponse.getGroupInfo().getCount().getRegisteredSuccess();
    	
    	log.info("성공회수 : " + registeredSuccess);
    	
    	
    	return ResponseEntity.ok(registeredSuccess);
    	
    	
    	
    }
    
    
    /** 회원 탈퇴 SMS 발송
     * @param toNumber
     * @return
     */
    @PostMapping("/sendOne/member/delete")
    public int memberDel(@RequestBody String toNumber) {
    	
    	
    	// 승인이냐, 거절이냐에 따라 문자 발송 양식 수정을 위해 키값 추가
    	SingleMessageSentResponse response = service.sendMemberSms("confirm", toNumber);
    	
    	// response : SMS 발송 결과
    	log.info("response : " + response);
    	
    	// 발송정보 != null == 성공
    	if(response != null) {
    		
    		return 1; // 성공
    		
    	}
    	
    	return 0; // 실패
    }
    
    /** 회원 다수 SMS 발송 - 탈퇴
     * @param action
     * @param paramMap
     * @return
     */
    @PostMapping("/sendMany/member/mulit/{actionForBackend}")
    public  ResponseEntity<Integer> sendManyMemberDelete(@PathVariable("actionForBackend") String action, 
			@RequestBody Map<String, List<Map<String, String>>> paramMap) {
    	
    	
    	List<Map<String, String>> members = paramMap.get("members");
		
        if (action == null) {
        	return ResponseEntity.ok(0);  // Invalid action
        }
    	
    	MultipleDetailMessageSentResponse multiResponse = service.sendMemberMultiSms(action, members);
    	
    	/* 
    	 	MultipleDetailMessageSentResponse(
    	 	failedMessageList=[], 
    	 	groupInfo=MultipleMessageSentResponse(groupId=G4V20240616021623JA4BH7W3CJZCYTV, 
    	 	messageId=null, accountId=24052703872352, statusMessage=null, statusCode=null, 
    	 	to=null, from=null, type=null, country=null, 
    	 	count=Count(total=2, sentTotal=0, sentFailed=0, sentSuccess=0, 
    	 	sentPending=0, sentReplacement=0, refund=0, registeredFailed=0, 
    	 	registeredSuccess=2)), messageList=null)
    	
    	*/
    	// 성공 결과 회수 가져오기
    	int registeredSuccess = multiResponse.getGroupInfo().getCount().getRegisteredSuccess();
    	
    	log.info("성공회수 : " + registeredSuccess);
    	
    	
    	return ResponseEntity.ok(registeredSuccess);
    	
    	
    	
    }
    
    /** 파트너 탈퇴 SMS 발송
     * @param toNumber
     * @return
     */
    @PostMapping("/sendOne/partner/delete")
    public int partnerDel(@RequestBody String toNumber) {
    	
    	
    	// 승인이냐, 거절이냐에 따라 문자 발송 양식 수정을 위해 키값 추가
    	SingleMessageSentResponse response = service.sendPartnerSms2("confirm", toNumber);
    	
    	// response : SMS 발송 결과
    	log.info("response : " + response);
    	
    	// 발송정보 != null == 성공
    	if(response != null) {
    		
    		return 1; // 성공
    		
    	}
    	
    	return 0; // 실패
    }
    
    /** 파트너 다수 SMS 발송 - 탈퇴
     * @param action
     * @param paramMap
     * @return
     */
    @PostMapping("/sendMany/mulit/partner/{actionForBackend}")
    public  ResponseEntity<Integer> sendManyPartnerDelete(@PathVariable("actionForBackend") String action, 
			@RequestBody Map<String, List<Map<String, String>>> paramMap) {
    	
    	
    	List<Map<String, String>> partners = paramMap.get("partners");
    	
		log.info("SMS 컨트롤러 partners: " + partners);
		log.info("SMS 컨트롤러 action: " + action);
		
        if (action == null || (!action.equals("confirm") && !action.equals("refuse"))) {
        	return ResponseEntity.ok(0);  // Invalid action
        }
    	
    	MultipleDetailMessageSentResponse multiResponse = service.sendPartnerManySms(action, partners);
    	
    	// response : SMS 발송 결과
    	log.info("multiResponse : " + multiResponse);
    	
    	/* 
    	 	MultipleDetailMessageSentResponse(
    	 	failedMessageList=[], 
    	 	groupInfo=MultipleMessageSentResponse(groupId=G4V20240616021623JA4BH7W3CJZCYTV, 
    	 	messageId=null, accountId=24052703872352, statusMessage=null, statusCode=null, 
    	 	to=null, from=null, type=null, country=null, 
    	 	count=Count(total=2, sentTotal=0, sentFailed=0, sentSuccess=0, 
    	 	sentPending=0, sentReplacement=0, refund=0, registeredFailed=0, 
    	 	registeredSuccess=2)), messageList=null)
    	
    	*/
    	// 성공 결과 회수 가져오기
    	int registeredSuccess = multiResponse.getGroupInfo().getCount().getRegisteredSuccess();
    	
    	log.info("성공회수 : " + registeredSuccess);
    	
    	
    	return ResponseEntity.ok(registeredSuccess);
    	
    	
    	
    }
    
    
    
}