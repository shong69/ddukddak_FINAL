package com.ddukddak.member.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.common.config.KakaoConfig;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.member.model.service.KakaoService;
import com.ddukddak.member.model.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@SessionAttributes({"loginMember"})
@RequestMapping("member")
@RequiredArgsConstructor
@Controller
@Slf4j
public class MemberController {
	
	private final MemberService service;
	
	// 카카오 로그아웃 URL
	//private static final String KAKAO_LOGOUT_URL = "https://kapi.kakao.com/v1/user/logout";
	
	@GetMapping("login")
	public String memberLogin(@RequestParam(value = "returnUrl", required = false) String returnUrl, Model model) {
	    model.addAttribute("returnUrl", returnUrl);
	    return "member/login";
	}
	
	@PostMapping("login")
	public String memberLogin(Member member, RedirectAttributes ra, Model model,
	                          @RequestParam(value="saveId", required=false) String saveId,
	                          @RequestParam(value="returnUrl", required=false) String returnUrl,
	                          HttpServletResponse resp) {
	    
	    Member loginMember = service.login(member);
	    String path = null;
	    
	    if(loginMember == null) {
	        ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
	        path = "login";
	    } else {
	        ra.addFlashAttribute("message", loginMember.getMemberId() + "님 환영합니다 :)");
	        model.addAttribute("loginMember", loginMember);
	        
	        // 관리자일 경우
	        if(loginMember.getAuthority() == 2) {
	        	
	        	return "redirect:/manager/main";
	        }
	        
	        path = (returnUrl != null && !returnUrl.isEmpty()) ? returnUrl : "/";
	        
	        Cookie cookie = new Cookie("saveId", loginMember.getMemberId());
	        cookie.setPath("/");
	        if(saveId != null) {
	            cookie.setMaxAge(60 * 60 * 24 * 30);
	        } else {
	            cookie.setMaxAge(0);
	        }
	        resp.addCookie(cookie);
	    }
	    return "redirect:" + path;
	}
	
	@GetMapping("logout")
	public String memberLogout(SessionStatus status, 
			 					RedirectAttributes ra, HttpSession session) {
		
		// 카카오 로그아웃
		// 네이버는 정책상 불가
		 
		// 세션에서 카카오 액세스 토큰 가져오기
		String accessToken = (String) session.getAttribute("kakaoAccessToken");
	    
	    
        if (accessToken != null) {
            // 카카오 연결 끊기 요청
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.postForEntity("https://kapi.kakao.com/v1/user/unlink", entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                // 연결 끊기 성공
                log.info("Kakao unlink successful");
            } else {
                // 연결 끊기 실패
                log.error("Kakao unlink failed");
            }

            // 세션에서 토큰 제거
            session.removeAttribute("kakaoAccessToken");
        }

	    // 스프링 세션 완료 처리
		status.setComplete();
		
		ra.addFlashAttribute("message", "로그아웃 되었습니다.");
		
		return "redirect:/";
	}
	

	/** [회원가입] 페이지 이동
	 * @return
	 */
	@GetMapping("signup")
	public String memberSignup() {
		
		return "member/signup";
	}
	
	
	/** [아이디 찾기] 페이지 이동
	 * @return
	 */
	@GetMapping("findId")
	public String memberFindId() {
		return "member/findId";
		
	}
	
	/** [아이디 찾기] 이름, 이메일 일치 여부 확인
	 * @return
	 */
	@PostMapping("memberNMCheck")
	@ResponseBody
	public int memberNMCheck(@RequestBody Member member) {
		
		return service.memberNMCheck(member);
	}
	
	
	
	/** [아이디 찾기] 이름, 휴대폰 일치 여부 확인
	 * @param memberTel
	 * @return
	 */
	@PostMapping("memberNTCheck")
	@ResponseBody
	public int memberNTCheck(@RequestBody Member member) {
		
		return service.memberNTCheck(member);
	}
	
	
	/** [아이디 찾기] 결과
	 * @param authType
	 * @return
	 */
	@PostMapping("resultId")
	public String memberResultId(
					@RequestParam Map<String, String> map,
					Model model
										) {
		/* 이메일 찾기 시 파라미터 (매핑, 액션 post -> get 임시 확인)
			http://localhost/member/resultId
			?userType=member
			&authType=email
			&emailNm=%EC%8B%A0%EC%88%98%EB%AF%BC < 신수민
			&memberEmail=soowagger%40gmail.com < @
			&authKey=n6zeyw
			&telNm=
			&memberTel=
			&smsAuthKey=
		*/
		
		/* 휴대폰 찾기 시 파라미터 (매핑, 액션 post -> get 임시 확인)
		 	http://localhost/member/resultId
		 	?userType=member
		 	&authType=tel
		 	&telNm=%EC%8B%A0%EC%88%98%EB%AF%BC
		 	&memberTel=01031235555
		 	&smsAuthKey=123456 
		 */
		
		
        // 파라미터 맵에서 authType과 userType 값을 추출
        String authType = map.get("authType");
        String userType = map.get("userType");
        String emailNm = map.get("emailNm");
        String telNm = map.get("telNm");
		String memberEmail = map.get("memberEmail");
        String memberTel = map.get("memberTel");
		
        // 이메일 찾기 멤버 객체
        Member emailFindMember = new Member();
        emailFindMember.setMemberName(emailNm);
        emailFindMember.setMemberEmail(memberEmail);
        
        // 휴대폰 찾기용 멤버 객체
        Member telFindMember = new Member();
        telFindMember.setMemberName(telNm);
        telFindMember.setMemberTel(memberTel);
        
        // 조회 결과 값 저장할 member 객체
        Member member = new Member();
        
        // 인증 타입이 이메일이면
		if(authType.equals("email")) {
			
			member = service.findIdByEmail(emailFindMember);
			 
		}
		
		// 인증 타입이 휴대폰이면
		if(authType.equals("tel")) {
			member = service.findIdByTel(telFindMember);
		}
		
		
		model.addAttribute("authType", authType);
		model.addAttribute("userType", userType);
		model.addAttribute("memberId", member.getMemberId());
		model.addAttribute("enrollDate", member.getEnrollDate());
		
		return "/common/resultId";
		
		
	}
	
	
	// * 비밀번호 찾기는 common.controller 에서 공통 처리
	
	
	/** 회원가입 - 아이디 중복 체크
	 * @param inputId
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkId")
	public int checkId(@RequestParam("memberId") String inputId) {
		
		return service.checkId(inputId);
	}
	
	
	/** 회원가입 - 닉네임 중복 체크
	 * @param inputNickname
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkNickname")
	public int checkNickname(@RequestParam("memberNickname") String inputNickname) {
		
		return service.checkNickname(inputNickname);
	}
	
	/** 회원가입 - 이메일 중복 체크
	 * @param inputEmail
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkEmail")
	public int checkEmail(@RequestParam("memberEmail") String inputEmail) {
		
		return service.checkEmail(inputEmail);
		
	}
	
	/** 회원가입 - 휴대폰 중복 체크
	 * @param inputTel
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkTel")
	public int checkTel(@RequestParam("memberTel") String inputTel) {
		
		return service.checkTel(inputTel);
	}
	
	
	
	/** 회원가입[제출]
	 * @param inputMember
	 * @param memberAddress
	 * @param ra
	 * @return
	 */
	@PostMapping("signup")
	public String signup(@ModelAttribute Member inputMember,
						@RequestParam("memberAddr") String[] memberAddr,
						RedirectAttributes ra) {
		
		
		// 회원가입 서비스 호출
		int result = service.signup(inputMember, memberAddr);
		
		String path = null;
		String message = null;
		
		if(result > 0) { // 성공 시
			message = inputMember.getMemberNickname() + "님의 가입을 환영합니다 :)";
			path = "/";
			
		} else { // 실패
			message = "회원가입 실패";
			path = "/member/signup";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
		
	}
	
	// 네이버, 카카오는 각 컨트롤러에서 구현
	// 카카오는 값 전달 받은 후 멤버 가입ㄱㄱ
	@PostMapping("kakaoData")
	@ResponseBody
	public int kakaoLogin(@RequestBody Map<String, String> map, HttpSession session) {
		
        String email = map.get("email");
        String nickName = map.get("nickname");
        String pw = map.get("pw");
        String profileImg = map.get("profileImage");
        
        log.info("멤버 컨트롤러단 emali : " + email);
        
        // 카카오 가입된 멤버 찾기
        Member member = service.findMemberByKakao(email);
        
        // 이후 네이버 로직과 동일
        
        log.info("멤버 컨트롤러단 member 결과 : " + member);
        
        if(member != null) {
        	
        	if(member.getSocialLoginType().equals("K")) {
        		
        		// 카카오 가입자 로그인
				session.setAttribute("loginMember", member);
				
				return 1;
        	} else {
        		
        		// 일반 or 네이버
        		return 2;
        	}
        } else {
        	
			// 1) 아이디
			String uuid = "카카오_" + UUID.randomUUID().toString();
        	
			// 2) 닉네임 길면 자르고
			if(nickName.length() > 10) {
				nickName = nickName.substring(0, 10);
			}
			
			// 3) 닉네임 중복 방지
			String originalNickname = nickName;
			
			int count = 1;
			 
			while(service.checkNickname(nickName) == 1) {
                nickName = originalNickname + count;
                count++;
			}
			
			
			// 3) 이름은 그냥 카카오(중복 가능하니까)
			String name = "카카오";
			
			Member newKakaoMember = Member.builder()
									.memberId(uuid)
									.memberPw(pw)
									.memberEmail(email)
									.memberName(name)
									.memberNickname(nickName)
									.profileImg(profileImg)
									.build();
			
			Member signinMember = service.kakaoSignup(newKakaoMember);
			
			log.info("멤버 컨트롤러단 사인업 결과 : " + signinMember);
			
			if(signinMember != null) {
				
				// 회원 가입 성공
				log.info("네이버 회원 가입 시 정보 : " + signinMember);
				// 로그인 실어주기
				session.setAttribute("loginMember", signinMember);
				
				return 3;
			} 
			
			
			log.info("멤버 컨트롤러단 사인업 결과 4번 : " + signinMember);
			// 로그인, 중복, 회원가입 다 실패
			return 4;
        }
	}
	
	
	
	
	
}
