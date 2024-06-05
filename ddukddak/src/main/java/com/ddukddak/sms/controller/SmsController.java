//package com.ddukddak.sms.controller;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.RequiredArgsConstructor;
//import net.nurigo.sdk.NurigoApp;
//import net.nurigo.sdk.message.model.Balance;
//import net.nurigo.sdk.message.model.Message;
//import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
//import net.nurigo.sdk.message.response.SingleMessageSentResponse;
//import net.nurigo.sdk.message.service.DefaultMessageService;
//
//@RestController
//@RequiredArgsConstructor
////@PropertySource("classpath:/key.properties") 
//@RequestMapping("sms")
//public class SmsController {
//	
////	@Value("${coolsms.api.key}")
////	private String apiKey;
////	
////	@Value("{coolsms.api.secret}")
////	private String apiSecret;
//	
//	final DefaultMessageService messageService;
//
//    public SmsController() {
//        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
//        this.messageService = NurigoApp.INSTANCE.initialize("", "", "https://api.coolsms.co.kr");
//    }
//    
////	String random = RandomStringUtils.randomNumeric(6);
////	
////	log.info("Random : " + random);
//   
//    /**
//     * 단일 메시지 발송 예제
//     */
//    @PostMapping("/send-one")
//    public SingleMessageSentResponse sendOne(String to) {
//    	
//        Message message = new Message();
//        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
//        message.setFrom("01032920409");
//        message.setTo("수신번호 입력");
//        message.setText("한글 45자, 영자 90자 이하 입력되면 자동으로 SMS타입의 메시지가 추가됩니다.");
//
//        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
//        System.out.println(response);
//
//        return response;
//    }
//
// 
//    /**
//     * 잔액 조회 예제
//     */
//    @GetMapping("/get-balance")
//    public Balance getBalance() {
//        Balance balance = this.messageService.getBalance();
//        System.out.println(balance);
//
//        return balance;
//    }
//}
