package com.ddukddak.partner.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.dto.Project;
import com.ddukddak.partner.model.dto.ProjectImg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("partner")
@Controller
@RequiredArgsConstructor
@SessionAttributes({"loginPartnerMember"})
@Slf4j
public class InteriorController {
	
	
	
	@GetMapping("interiorPortfolioEditMain")
    public String interiorPortfolioEditMain(@SessionAttribute("loginPartnerMember") Partner loginPartnerMember, RedirectAttributes ra) {
        if (loginPartnerMember.getPartnerType() != 1) {
            ra.addFlashAttribute("message", "접근 제한된 서비스 입니다");
            return "redirect:/partner/main";
        }
        return "partner/interior/interiorPortfolioEdit/interiorPortfolioEditMain";
    }
	
	@GetMapping("projectDetail")
	public String projectDetail() {
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
								) {
		
		Project project = new Project();
		
		project.setProjectName(inputProjectName);
		project.setHousingType(housingType);
		project.setWorkForm(workForm);
		project.setConstructionCost(constructionCost);
		project.setWorkArea(workArea);
		project.setRegion(region);
		project.setConstructionYear(constructionYear);
		project.setFamilySize(familySize);
		
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
		
		
		return "";
	}
}











