package com.ddukddak.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("manager")
public class ManagerController {

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
	
	
	/** 파트너 가입 승인 관리
	 * @return
	 */
	@GetMapping("partnerPass")
	public String partnerPass(Model model) {
		
		
		
		return "manager/partnerPass";
	}
	
	
	
}
