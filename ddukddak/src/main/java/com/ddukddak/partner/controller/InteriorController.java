package com.ddukddak.partner.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.dto.Portfolio;
import com.ddukddak.partner.model.dto.Project;
import com.ddukddak.partner.model.dto.ProjectImg;
import com.ddukddak.partner.model.service.InteriorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("partner")
@Controller
@RequiredArgsConstructor
@SessionAttributes({"loginPartnerMember"})
@Slf4j
public class InteriorController {
	
	private final InteriorService service;
	
	@GetMapping("interiorPortfolioEditMain")
    public String interiorPortfolioEditMain(@SessionAttribute("loginPartnerMember") Partner loginPartnerMember, 
    										RedirectAttributes ra,
    										Model model) {
		
        if (loginPartnerMember.getPartnerType() != 1) {
        	
            ra.addFlashAttribute("message", "접근 제한된 서비스 입니다");
            return "redirect:/partner/main";
            
        }
        
        log.info("portfolioNo : " + loginPartnerMember.getPortfolioNo());
        
        Portfolio portfolio = service.selectPortfolio(loginPartnerMember.getPortfolioNo());
        
        Project mainProject = service.selectMainProject(loginPartnerMember.getPortfolioNo());
        log.info("projectNo : " + mainProject.getProjectNo());
//        List<ProjectImg> mainProjectImgList = service.selectImageList(mainProject.getProjectNo());
        List<Project> projectList = service.selectProjectList(loginPartnerMember.getPortfolioNo());
        
        model.addAttribute("mainProject", mainProject);
//        model.addAttribute("mainProjectImgList", mainProjectImgList);
        model.addAttribute("projectList", projectList);
        
        return "partner/interior/interiorPortfolioEdit/interiorPortfolioEditMain";
    }
	
	@GetMapping("projectDetail/{projectNo:[0-9]+}")
	public String projectDetail(@PathVariable("projectNo") int projectNo) {
		
		Project project = service.selectProject(projectNo);
		
		return "partner/interior/interiorPortfolioEdit/projectDetail";
	}
	
	@GetMapping("interiorChatWithManager")
	public String interiorChatWithManager() {
		return "partner/interior/interiorChatWithManager/interiorChatWithManager";
	}
	
	@GetMapping("interiorChatWithManagerPopup")
	public String interiorChatWithManagerPopup() {
		return "partner/interior/interiorChatWithManager/interiorChatWithManagerPopup";
	}
	
	@GetMapping("interiorChatWithUser")
	public String interiorChatWithUser() {
		return "partner/interior/interiorChatWithUser/interiorChatWithUser";
	}
	
	@GetMapping("registProject")
	public String registProject() {
		return "partner/interior/interiorPortfolioEdit/registProject";
	}
	
	@PostMapping("registProject")
	public String registProject(Model model,
								@RequestParam("projectName") String inputProjectName,
								@RequestParam("housingType") String housingType,
								@RequestParam("workForm") String workForm,
								@RequestParam("constructionCost") Number constructionCost,
								@RequestParam("workArea") Number workArea,
								@RequestParam("region") String region,
								@RequestParam("constructionYear") String constructionYear,
								@RequestParam("familySize") String familySize,
								@RequestParam("mainImg") String mainImgFileName,
								@RequestParam("images") List<MultipartFile> images,
								@SessionAttribute("loginPartnerMember") Partner loginPartnerMember,
								RedirectAttributes ra
								) throws IOException {
		
		Project project = new Project();
		
		log.info("constructionYear : " + constructionYear);
		
		project.setProjectName(inputProjectName);
		project.setHousingType(housingType);
		project.setWorkForm(workForm);
		project.setConstructionCost(constructionCost);
		project.setWorkArea(workArea);
		project.setRegion(region);
		project.setConstructionYear(constructionYear);
		project.setFamilySize(familySize);
		project.setPortfolioNo(loginPartnerMember.getPortfolioNo());
		project.setPartnerNo(loginPartnerMember.getPartnerNo());
		
		log.info(constructionYear);
		
		MultipartFile mainImg = null;
		
		String message = null;
		String path = null;
		
		for(MultipartFile image : images) {
			
			if(image.getOriginalFilename().equals(mainImgFileName)) {
				mainImg = image;
				log.info("mainImg : " + mainImg);
				break;
			}
			
		}
		
		List<MultipartFile> imgList = new ArrayList<>();
		
		imgList.remove(mainImg);	// 중복된 mainImg 리스트에서 삭제
		imgList.add(0, mainImg);	// 다시 mainImg 를 배열 0번째 자리에 추가
		
		int projectNo = service.insertProject(project, imgList);
		
		if(projectNo > 0) {
			path = "/interior/interiorPortfolioDetail/" + projectNo;
			message = "포트폴리오 프로젝트 등록이 완료되었습니다.";
		} else {
			path = "/partner/interiorPortfolioEditMain";
			message = "포트폴리오 프로젝트 등록에 실패하였습니다.";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
		
	}
}











