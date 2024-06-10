package com.ddukddak.member.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.member.model.dto.Member;
import com.ddukddak.member.model.service.MemberService;
import com.ddukddak.partner.model.dto.Partner;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes({"loginMember"})
@RequestMapping("member")
@RequiredArgsConstructor
@Controller
@Slf4j
public class MemberController {

	private final MemberService service;
	
	
	@GetMapping("login")
	public String memberLogin() {
		return "member/login";
	}
	
	@PostMapping("login")
	public String memberLogin(Member member, RedirectAttributes ra, Model model, @RequestParam(value="saveId", required=false) String saveId, HttpServletResponse resp) {
		
//		String smsAuthKey = Utility.RandomNumber6();
//		
//		log.info("인증 번호 테스트 : {}", smsAuthKey);

		
		Member loginMember = service.login(member);
		
		String path = null;
		
		if(loginMember == null) {
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
			path = "login";
		}
		if(loginMember != null) {
			ra.addFlashAttribute("message", loginMember.getMemberId()+"님 환영합니다");
			
			model.addAttribute("loginMember", loginMember);
			path="/";
			
			Cookie cookie = new Cookie("saveId", loginMember.getMemberId());
			
			cookie.setPath("/");
			
			if(saveId != null) {
				cookie.setMaxAge(60 *60 * 24 * 30);
				
			} else { // 미체크시 
				cookie.setMaxAge(0);
			}
			
			resp.addCookie(cookie);

		
		}
		return "redirect:" + path;
	}
	
	@GetMapping("logout")
	public String memberLogout(SessionStatus status, 
			 RedirectAttributes ra) {
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
	
	
}
