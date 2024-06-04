package com.ddukddak.email.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.ddukddak.email.model.mapper.EmailMapper;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	
	private final JavaMailSender mailSender; // EmailConfig
	
	private final SpringTemplateEngine templateEngine; // HTML -> Java
	
	private final EmailMapper mapper;
	
	

	/** 메일 보내기
	 *
	 */
	@Override
	public String sendEmail(String pageName, String email) {
		
		String authKey = createAuthKey();
		
		try {
			// 제목
			String subject = null;
			
			switch(pageName) {
			
				//case "signup" : subject = "[뚝딱뚝딱] 이메일 인증번호 : " + authKey; break;
				case "findId" : subject = "[뚝딱뚝딱] 이메일 인증번호 : " + authKey; break;
			
			}
			
			// MimeMessage : Java에서 메일을 보내는 객체
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			// MimeMessageHelper : Spring에서 제공하는 메일 발송 도우미(간단 + 타임리프)
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			// 1번 매개변수 : MimeMessage
			// 2번 매개변수 : 파일 전송 사용 ? true / false
			// 3번 매개변수 : 문자 인코딩 지정
			
			helper.setTo(email); // 받는 사람 이메일 지정
			helper.setSubject(subject); // 이메일 제목 지정
			
			helper.setText( loadHtml(authKey, pageName), true ); // html보낼거임(추후 변경 예정)
			// HTML 코드 해석 여부 true (innerHTML 해석)
			
			// CID(Content-ID)를 이용해 메일에 이미지 첨부
			// (파일첨부와는 다름, 이메일 내용 자체에 사용할 이미지 첨부)
			// logo 추가 예정
			//helper.addInline("logo", new ClassPathResource("static/images/logo.jpg"));
			// -> 로고 이미지를 메일 내용에 첨부하는데
			//    사용하고 싶으면 "logo"라는 id를 작성해라
			
			
			// 메일 보내기
			mailSender.send(mimeMessage);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		// 이메일 + 인증번호를 "TB_AUTH_KEY" 테이블 저장
		Map<String, String> map = new HashMap<>();
		
		map.put("authKey", authKey);
		map.put("email", email);
		
		// 1) 해당 이메일이 DB에 존재하는 경우가 있을 수 있기 때문에
		//    수정(update)을 먼저 수행
		//    -> 1 반환 == 업데이트 성공 == 이미 존재해서 인증번호 변경
		//    -> 0 반환 == 업데이트 실패 == 이메일 존재 X --> INSERT 시도	
		int result = mapper.updateAuthKey(map);
		
		// 2) update 실패 시 insert 시도
		if(result == 0) {
			
			result = mapper.insertAuthKey(map);
			
		} 
		
		// 수정, 삽입 후에도 result 가 0 == 실패
		if(result == 0) return null;
		
		// 성공
		return authKey; // 오류 없이 완료되면 authKey 반환
		
	}

	
	
	/** HTML 파일 -> String 변환(타임리프 적용)
	 * @param authKey
	 * @param pageName
	 * @return
	 */
	private String loadHtml(String authKey, String pageName) {
		
		Context context = new Context();
		
		// 타임리프가 적용된 HTML에서 사용할 값 추가
		context.setVariable("authKey", authKey);
		
		
		// templates/email 폴더에서 htmlName과 같은
		// ~.html 파일 내용을 읽어와 String으로 변환
		return templateEngine.process("email/" + pageName, context);

	}
	
	
	

	/** 인증번호 난수 생성
	 * @return authKey
	 */
	private String createAuthKey() {
		
		String key = "";
		
		for(int i=0; i<6; i++) {
			
			int sel1 = (int)(Math.random() * 3);
			
			if(sel1 == 0) {

				int num = (int)(Math.random() * 10); // 0-9
				key += num; // 0인 경우 숫자이기 때문에 바로 누적
				
			} else {
				
				char ch = (char)(Math.random() * 26 + 65); // A-Z
				
				int sel2 = (int)(Math.random() * 2); // 0 : 소문자, 1 : 대문자
				
				if(sel2 == 0) {
					ch = (char)(ch+ ('a' - 'A')); // 0 : 소문자
				}
				key += ch;
			}
		}
		return key;
	}



	// 인증번호 일치 여부 확인
	@Override
	public int checkAuthKey(Map<String, Object> map) {
		
		return mapper.checkAuthKey(map);
	}

}
