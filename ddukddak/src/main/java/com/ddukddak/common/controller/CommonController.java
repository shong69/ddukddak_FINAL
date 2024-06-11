package com.ddukddak.common.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		
		
		return 0;
	}
	
	@PostMapping
	@ResponseBody
	public String changePw() {
		
		return "common/changePw";
	}
	
}
