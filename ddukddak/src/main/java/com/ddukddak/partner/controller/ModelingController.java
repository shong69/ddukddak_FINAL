package com.ddukddak.partner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("partner/interior")
@Controller
@RequiredArgsConstructor
public class ModelingController {


	//방 디자인 메인화면
	@GetMapping("modeling/main")
	public String modelingMain() {
		return "partner/interior/modeling/modelingMain";
	}
	
	// 예상견적 확인화면
	@GetMapping("modeling/estimate")
	public String modelingEstimate() {
		return "partner/interior/modeling/modelingEstimate";
	}
	
	// 모델링 테스트
	@GetMapping("modeling/test")
	public String modelingTest() {
		return "partner/interior/modeling/modelingTest";
	}
}
