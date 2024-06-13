package com.ddukddak.partner.controller;

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

import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.service.PartnerService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("partner")
@SessionAttributes("{loginPartnerMember}")
@RequiredArgsConstructor
@Slf4j
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
		
		log.info("loginPartnerMember : " + loginPartnerMember);
		
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
	
	
	
	/** 파트너 메인페이지 이동
	 * @return
	 */
	@GetMapping("main")
	public String partnerMain() {
		return "partner/partnerMain";
	}
	
	
	/** 파트너 이름, 휴대폰 일치 여부 확인(비동기)
	 * @param partner
	 * @return
	 */
	@ResponseBody
	@PostMapping("partnerNTCheck")
	public int partnerNTCheck(@RequestBody Partner partner) {
		
		return service.partnerNTCheck(partner);
	}
	
	
	/** [파트너] 아이디 찾기 결과
	 * @param inputMember
	 * @return
	 */
	@PostMapping("resultId")
	public String partnerResultId(
						@RequestParam Map<String, String> map,
						Model model) {
		
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
	    String telNm = map.get("telNm");
	    String partnerTel = map.get("partnerTel");
		
	    // 휴대폰 찾기용 파트너 객체
	    Partner telFindPartner = new Partner();
	    telFindPartner.setPartnerCeoName(telNm);
	    telFindPartner.setPartnerTel(partnerTel);
	    
	    // 조회 결과 값 저장할 파트너  객체
	    Partner partner = new Partner();
		
	    partner = service.findIdByTel(telFindPartner);
		
	    log.info("partner 정보 : " + partner);
	    
		model.addAttribute("authType", authType);
		model.addAttribute("userType", userType);
		model.addAttribute("partnerId", partner.getPartnerId());
		model.addAttribute("enrollDate", partner.getEnrollDate());
		model.addAttribute("partnerType", partner.getPartnerType());
			
			
		return "/common/resultId";
		
	}
	
	/** 파트너 회원가입 - 사업자등록번호 중복 체크
	 * @param inputBN
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkBusinessNum")
	public int checkBusinessNum(@RequestParam("partnerBusinessNum") String inputBN) {
		
		log.info("inputBN : " + inputBN);
		
		return service.checkBusinessNum(inputBN);
	}
	
	/** 파트너 회원가입 - 상호명 중복 체크
	 * @param inputBName
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkBusinessName")
	public int checkBusinessName(@RequestParam("partnerBusinessName") String inputBName) {
		return service.checkBusinessName(inputBName);
	}
	
	/** 파트너 회원가입 - 아이디 중복 체크
	 * @param inputId
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkId")
	public int checkId(@RequestParam("partnerId") String inputId) {
		return service.checkId(inputId);
	}
	
	/** 파트너 회원가입 - 휴대폰 중복 체크
	 * @param inputTel
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkTel")
	public int checkTel(@RequestParam("partnerTel") String inputTel) {
		return service.checkTel(inputTel);
	}
}
