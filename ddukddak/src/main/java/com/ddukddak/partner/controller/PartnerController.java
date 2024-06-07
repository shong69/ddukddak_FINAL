package com.ddukddak.partner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.member.model.dto.Member;
import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.service.PartnerService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

@Slf4j
@Controller
@RequestMapping("partner")
@SessionAttributes({"loginPartnerMember"})
@RequiredArgsConstructor
public class PartnerController {

	private final PartnerService service;
	
	@GetMapping("login")
	public String partnerLogin() {		
		
		return "/partner/login";
	}
	
	@PostMapping("login")
	public String partnerLogin(Partner partner, RedirectAttributes ra, Model model, @RequestParam(value="saveId", required=false) String partnerSaveId, HttpServletResponse resp) {
		
		Partner loginPartnerMember = service.login(partner);
		
		String path = null;
		
		if(loginPartnerMember == null) {
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
			path = "login";
		}
		if(loginPartnerMember != null) {
			ra.addFlashAttribute("message", loginPartnerMember.getPartnerId()+"님 환영합니다");
			
			model.addAttribute("loginPartnerMember", loginPartnerMember);
			path="/partner/main";
			
			Cookie cookie = new Cookie("partnerSaveId", loginPartnerMember.getPartnerId());
			
			cookie.setPath("/");
			
			if(partnerSaveId != null) {
				cookie.setMaxAge(60 *60 * 24 * 30);
				
			} else { // 미체크시 
				cookie.setMaxAge(0);
			}
			
			resp.addCookie(cookie);
		}
		
		
		
		
		return "redirect:" + path;
	}
	
	
	/** 로그 아웃
	 * @param status
	 * @return
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status, 
						 RedirectAttributes ra) {
		
		status.setComplete();
		
		ra.addFlashAttribute("message", "로그아웃 되었습니다.");
		
		return "redirect:/";
	}
	
	
	/** [파트너] 회원가입 페이지 이동
	 * @param choice : 1 : 시공사 / 2 : 판매사
	 * @param model
	 * @return
	 */
	@GetMapping("signup")
	public String partnerSignup(
						@RequestParam(value = "choice", required = false) String choice, 
						Model model) {
		
		model.addAttribute("choice", choice);
		
		return "partner/signup";
	}
	

	/** [파트너] 아이디 찾기 페이지 이동
	 * @return
	 */
	@GetMapping("findId")
	public String partnerFindId() {
		return "partner/findId";
	}	
	
	/** [파트너] 아이디 찾기 결과
	 * @param inputMember
	 * @return
	 */
	@PostMapping("resultId")
	public String partnerResultId(Member inputMember) {
		
		
		return "/common/resultId";
	}
	
	// * 비밀번호 찾기는 common.controller 에서 공통 처리 	

	
	/** 파트너 메인페이지 이동
	 * @return
	 */
	@GetMapping("main")
	public String partnerMain() {
		return "partner/partnerMain";
	}
	
}
