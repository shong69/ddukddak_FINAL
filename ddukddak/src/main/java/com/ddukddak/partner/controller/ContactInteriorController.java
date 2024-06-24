package com.ddukddak.partner.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.member.model.dto.Member;
import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.dto.Portfolio;
import com.ddukddak.partner.model.dto.Project;
import com.ddukddak.partner.model.service.InteriorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("interior")
@Controller
@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
public class ContactInteriorController {
	
	private final InteriorService service;

	@GetMapping("interiorList")
	public String interiorList(Model model,
							   @RequestParam(value="cp", required=false, defaultValue="1") int cp,
							   @RequestParam(value="query", required = false) String query) {
		
		Map<String, Object> map = null;
		
		if(query == null) {
			
			map = service.selectInteriorList(cp);
			
		} else {
			
			map = service.searchInteriorList(query, cp);
			
		}
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("interiorList", map.get("interiorList"));
		model.addAttribute("query", query);
		
		log.info("interiorList : " + map.get("interiorList"));
		
		return "partner/interior/interiorList";
	}
	
	@GetMapping("interiorPortfolio/{portfolioNo:[0-9]+}")
	public String interiorPortfolio(@PathVariable("portfolioNo") int portfolioNo,
									@SessionAttribute(value="loginMember", required=false) Member loginMember,
									Model model,
									RedirectAttributes ra,
									HttpServletRequest req,
									HttpServletResponse resp) {

		
		log.info("포트폴리오 번호 : " + portfolioNo);
		
		Portfolio portfolio = service.selectPortfolio2(portfolioNo);
		
        
		Map<String, Object> map = service.selectProjectList(portfolioNo);
		
		portfolio.setMainProject( (Project) map.get("mainProject") );
		
		log.info("portfolio : " + portfolio);
		
		List<Project> projectList = (List<Project>) map.get("projectList");
		
		List<List<Project>> projectChunks = chunkProjects(projectList, 3);
		
		model.addAttribute("projectChunks", projectChunks);
        model.addAttribute("portfolio", portfolio);
		
        return "partner/interior/interiorPortfolio";
 
	}
        
	
	private List<List<Project>> chunkProjects(List<Project> projects, int chunkSize) {
        List<List<Project>> projectChunks = new ArrayList<>();
        for (int i = 0; i < projects.size(); i += chunkSize) {
        	projectChunks.add(projects.subList(i, Math.min(i + chunkSize, projects.size())));
        }
        return projectChunks;
    }
	
	@GetMapping("interiorPortfolioDetail/{projectNo:[0-9]+}")
	public String interiorPortfolioDetail(Model model, 
										  @PathVariable("projectNo") int projectNo,
										  @RequestParam("portfolioNo") int portfolioNo) {
		
		
		Portfolio portfolio = service.selectPortfolio2(portfolioNo);
		
		Project project = service.selectProject(projectNo);
		
		log.info("project : " + project);
		
		Map<String, Object> map = service.selectProjectList(portfolioNo);
		
		List<Project> projectList = (List<Project>) map.get("projectList");
		
		List<List<Project>> projectChunks = chunkProjects(projectList, 3);
		
		model.addAttribute("project", project);
		model.addAttribute("projectChunks", projectChunks);
		model.addAttribute("portfolio", portfolio);
		
		return "partner/interior/projectDetail";
	}
	
}
