package com.ddukddak.manager.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ddukddak.manager.model.service.ManagerService;
import com.ddukddak.partner.model.dto.Partner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("manager")
@RequiredArgsConstructor
@Slf4j
public class ManagerController {
	
	private final ManagerService service;
	
	@GetMapping("main")
	public String managerMain() {
		return"manager/managerMain";
	}
	
	@GetMapping("chatService")
	public String chatWithMember() {
		return"manager/chatWithMember";
	}
	@GetMapping("chatPopup")
	public String chatWithMemberPopup() {
		return "manager/chatWithMemberPopup";
	}
	
	@GetMapping("registerLiveCommerce")
	public String registerLiveCommerce() {
		return"manager/registerLiveCommerce";
	}
	
	
	/** 파트너 관리
	 * @return
	 */
	@GetMapping("partnerList")
	public String partnerList() {
		return "manager/partnerList";
	}
	
	
	/** 파트너 가입 승인 관리 - 목록 조회
	 * @return
	 */
	@GetMapping("partnerPass")
	public String partnerPass(@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
							Model model) {
		
		// 검색 기능이 없기 때문에 @RequestParam 사용 X
		
		Map<String, Object> map = service.selectPassList(cp);
		
		//log.info("map : " + map.get("passList"));
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("passList", map.get("passList"));
		
		
		return "manager/partnerPass";
	}
	
	/** 파트너 가입 승인
	 * @param partNo
	 * @return
	 */
	@ResponseBody
	@PostMapping("partner/confirm")
	public int passConfirm(@RequestBody Partner partner) {
		
		int partnerNo = partner.getPartnerNo();
		String partnerTel = partner.getPartnerTel();
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("partnerNo", partnerNo);
		map.put("partnerTel", partnerTel);
		
		int result = service.passConfirm(map);
		
//		int result = service.passConfirm(partnerNo);
		
		// 1 : 성공, 0 : 실패
		return 0;
	}
	
	
}
