package com.ddukddak.partner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ddukddak.member.model.dto.Member;

@Controller
@RequestMapping("partner")
public class PartnerController {

	@GetMapping("login")
	public String partnerLogin() {
		
		return "/partner/login";
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
