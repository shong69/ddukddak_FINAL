package com.ddukddak.partner.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ddukddak.partner.model.service.InteriorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("partner/interior")
@Controller
@RequiredArgsConstructor
@Slf4j
public class ModelingController {
	
	private final InteriorService interiorService;


	//방 디자인 메인화면
	@GetMapping("modeling/main")
	public String modelingMain() {
		return "partner/interior/modeling/modelingMain";
	}
	
	// 예상견적 확인화면
	@PostMapping("modeling/estimate")
	public String modelingEstimate(@RequestParam("floorPlan") String floorPlan,
									@RequestParam("ThreedModeling") String ThreedModeling,
								   @RequestParam(value="cp", required=false, defaultValue="1") int cp,
								   @RequestParam(value="query", required = false) String query,
									Model model) {
		// 사진값 넘겨주기
		model.addAttribute("floorPlan", floorPlan);
		model.addAttribute("ThreedModeling", ThreedModeling);
		
		// 시공사 리스트 가져오기
		Map<String, Object> map = null;
		
		if(query == null) {
			
			map = interiorService.selectInteriorList(cp);
			
		} else {
			
			map = interiorService.searchInteriorList(query, cp);
			
		}
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("interiorList", map.get("interiorList"));
		model.addAttribute("query", query);
		
		return "partner/interior/modeling/modelingEstimate";
	}
	
	// 모델링 테스트
	@GetMapping("modeling/test")
	public String modelingTest() {
		return "partner/interior/modeling/modelingTest";
	}
}
