package com.ddukddak.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ddukddak.manager.model.service.ManagerService;

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
	
	/** 파트너 승인 처리
	 * @param partNo
	 * @return
	 */
	@ResponseBody
	@PostMapping("partner/confirm")
	public int passConfirm(@RequestBody String partnerNo) {
		
		log.info("partnerNo : " + partnerNo);
		
		// 1 : 성공, 0 : 실패
		return service.passConfirm(partnerNo);
	}
	
	/** 파트너 승인 처리
	 * @param partNo
	 * @return
	 */
	@ResponseBody
	@PostMapping("partner/refuse")
	public int passRefuse(@RequestBody String partnerNo) {
		
				
		// 1 : 성공, 0 : 실패
		return service.passRefuse(partnerNo);
	}
	
	/** 파트너 다중 승인, 거절 처리 및 SMS 발송까지
	 * @param action
	 * @return
	 */
	@PostMapping("partner/multi/{actionForBackend}")
	public ResponseEntity<Integer> updateMultiPass(@PathVariable("actionForBackend") String action, 
				@RequestBody Map<String, List<Map<String, String>>> paramMap) {
		//

		List<Map<String, String>> partners = paramMap.get("partners");
		
		log.info("컨트롤러 partners: " + partners);
        if (action == null || (!action.equals("confirm") && !action.equals("refuse"))) {
        	return ResponseEntity.ok(0);  // Invalid action
        }

       
        // 1 : 성공 / 0 실패
        int result = service.updateMultiPass(action, partners);
        

        return ResponseEntity.ok(result);
	}
	
	
}
