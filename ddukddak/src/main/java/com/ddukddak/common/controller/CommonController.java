package com.ddukddak.common.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.common.model.CommonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("common")
@RequiredArgsConstructor
@Slf4j
public class CommonController {
	
	private final CommonService service;

	
	/** 비밀번호 찾기 페이지 이동
	 * @return
	 */
	@GetMapping("findPw")
	public String commonFindPw() {
		
		return "common/findPw";
	}
	
	
	
	/** 비밀번호 찾기 - 아이디 검색(공통)
	 * @param map
	 * @return
	 */
	@PostMapping("idCheck")
	@ResponseBody
	public int idCheck(@RequestBody String id) {
		
		
		return service.idCheck(id);
		
	}
	
	/** 비밀번호 찾기 - 아이디, 휴대폰 일치 여부 확인
	 * @param map
	 * @return
	 */
	@PostMapping("commonITCheck")
	@ResponseBody
	public int commonITCheck(@RequestBody Map<String, String> map) {
		
		log.info("map : " + map);
		/* 
		 	const commonITCheck = {
        		"id" : telNm.value,
        		"tel" : inputTel.value
    		}
		 */
		String id = null;
		
		id = service.commonITCheck(map);
		
		if(id != null) return 1; // id 조회 성공 시
		
		
		return 0; // 조회 실패 시
	}
	
	/** 비밀번호 재설정
	 * @param map
	 * @param model
	 * @return
	 */
	@PostMapping("changePw")
	public String changePw(@RequestParam Map<String, String> map, 
							Model model, RedirectAttributes ra) {
		
		/*
		 http://localhost/common/changePw?
		 inputId=sm
		 &inputTel=01032920409
		 &smsAuthKey=228324
		 &newPw=12341234
		 &newConfirmPw=12341234
		 */
		
		int result = service.changePw(map);
		
		log.info("비번 변경 result : " + result);
		
		String message = null;
		String path = null;
		
		// 비밀번호 중복
		if(result == -1) {
			
			message = "기존 비밀번호와 동일한 비밀번호를 사용할 수 없습니다.";
			path = "/common/findPw";
			
		// 업데이트 실패
		} else if(result == 0) {
			
			message = "비밀번호 변경에 실패하였습니다. 다시 시도해 주세요.";
			path = "/common/findPw";
			
		} else {
			
			message = "비밀번호가 정상적으로 변경되었습니다.";
			path = "/member/login";
			
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
}
