package com.ddukddak.partner.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.dto.Portfolio;
import com.ddukddak.partner.model.dto.Project;
import com.ddukddak.partner.model.dto.ProjectImg;
import com.ddukddak.partner.model.service.InteriorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.sql.ARRAY;

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
        
//      log.info("portfolioNo : " + loginPartnerMember.getPortfolioNo());
//        
        Portfolio portfolio = service.selectPortfolio(loginPartnerMember.getPortfolioNo());
        log.info("portfolio : " + portfolio);
        
        if(portfolio == null) {
        	ra.addFlashAttribute("message", "포트폴리오, 프로젝트 작성이 필요합니다.");
//        	int result = service.insertPortfolio(loginPartnerMember.getPartnerNo());
        	return "redirect:/partner/registProject";
        }
        
        Map<String, Object> map = service.selectProjectList(portfolio.getPortfolioNo());
        
//        Project mainProject = service.selectMainProject(loginPartnerMember.getPortfolioNo());
//        log.info("mainProject : " + mainProject);
//      List<ProjectImg> mainProjectImgList = service.selectImageList(mainProject.getProjectNo());
//        List<Project> projectList = service.selectProjectList(loginPartnerMember.getPortfolioNo());
        
        portfolio.setMainProject( (Project) map.get("mainProject") );
        
        List<Project> projectList = (List<Project>) map.get("projectList");
        
        List<List<Project>> projectChunks = chunkProjects(projectList, 3);
        
//        log.info("포트폴리오 프로젝트 리스트 : " + projectList);
//        log.info("청크 확인 : " + projectChunks);
        

        
//        log.info("확인 : " + portfolio.getProjectList().get(5).getThumbnail());
//        log.info("mainProject : " + portfolio.getMainProject());
//        log.info("확인 : " + portfolio.getMainProject().getImgList().get(2));
        
        model.addAttribute("projectChunks", projectChunks);
        model.addAttribute("portfolio", portfolio);
        
        return "partner/interior/interiorPortfolioEdit/interiorPortfolioEditMain";
    }
	
	
	private List<List<Project>> chunkProjects(List<Project> projects, int chunkSize) {
        List<List<Project>> projectChunks = new ArrayList<>();
        for (int i = 0; i < projects.size(); i += chunkSize) {
        	projectChunks.add(projects.subList(i, Math.min(i + chunkSize, projects.size())));
        }
        return projectChunks;
    }
	
	
	
	@GetMapping("projectDetail/{projectNo:[0-9]+}")
	public String projectDetail(@PathVariable("projectNo") int projectNo,
								Model model,
								RedirectAttributes ra) {
		
		Project project = service.selectProject(projectNo);
		
//		log.info("project : " + project);
		
//		log.info("imgList : " + project.getImgList());
		
		model.addAttribute(project);
		
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
								@RequestParam("projectContent") String inputProjectContent,
								@RequestParam("housingType") String housingType,
								@RequestParam("workForm") String workForm,
								@RequestParam("constructionCost") String constructionCost,
								@RequestParam("workArea") String workArea,
								@RequestParam("region") String region,
								@RequestParam("constructionYear") String constructionYear,
								@RequestParam("familySize") String familySize,
								@RequestParam("mainImg") String mainImgFileName,
								@RequestParam("images") List<MultipartFile> images,
								@SessionAttribute("loginPartnerMember") Partner loginPartnerMember,
								RedirectAttributes ra
								) throws IOException {
		
		Project project = new Project();
		
//		log.info("constructionYear : " + constructionYear);
		
		project.setProjectName(inputProjectName);
		project.setProjectContent(inputProjectContent);
		project.setHousingType(housingType);
		project.setWorkForm(workForm);
		project.setConstructionCost(constructionCost);
		project.setWorkArea(workArea);
		project.setRegion(region);
		project.setConstructionYear(constructionYear);
		project.setFamilySize(familySize);
		project.setPortfolioNo(loginPartnerMember.getPortfolioNo());
		project.setPartnerNo(loginPartnerMember.getPartnerNo());
		
//		log.info("images : " + images);
		MultipartFile mainImg = null;
		
		String message = null;
		
		for(MultipartFile image : images) {
			
			if(image.getOriginalFilename().equals(mainImgFileName)) {
				mainImg = image;
//				log.info("mainImg : " + mainImg);
				break;
			}
			
		}
		
		List<MultipartFile> imgList = new ArrayList<>(images);
		
		imgList.remove(mainImg);	// 중복된 mainImg 리스트에서 삭제
		imgList.add(0, mainImg);	// 다시 mainImg 를 배열 0번째 자리에 추가
		
//		log.info("imgList : " + imgList);
		
		int projectNo = service.insertProject(project, imgList);
		
		if(projectNo > 0) {
			
			message = "포트폴리오 프로젝트 등록이 완료되었습니다.";
		} else {
			
			message = "포트폴리오 프로젝트 등록에 실패하였습니다.";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:/partner/interiorPortfolioEditMain";
		
	}
	
	@PostMapping("updateProject")
	public String updateProject(@RequestParam("projectNo") int projectNo,
								@RequestParam("images") List<MultipartFile> images,
							 	@RequestParam("housingType") String housingType,
							 	@RequestParam("workArea") String workArea,
							 	@RequestParam("workForm") String workForm,
							 	@RequestParam("constructionCost") String constructionCost,
							 	@RequestParam("region") String region,
							 	@RequestParam("constructionYear") String constructionYear,
							 	@RequestParam("familySize") String familySize,
							 	@RequestParam("projectContent") String projectContent,
							 	@SessionAttribute("loginPartnerMember") Partner loginPartnerMember,
							 	RedirectAttributes ra,
							 	Model model) throws IllegalStateException, IOException {
		
		Project project = new Project();
		
		project.setPartnerNo(loginPartnerMember.getPartnerNo());
		project.setProjectNo(projectNo);
		project.setHousingType(housingType);
		project.setWorkArea(workArea);
		project.setWorkForm(workForm);
		project.setConstructionCost(constructionCost);
		project.setRegion(region);
		project.setConstructionYear(constructionYear);
		project.setFamilySize(familySize);
		project.setProjectContent(projectContent);
		
		
		int result = service.updateProject(project, images);
		String path = null;
		String message = null;
		
		
		log.info("프로젝트 : " + project);
		log.info("이미지 : " + images);
		
		if(result > 0) {
			path = "/partner/interiorPortfolioEditMain";
			message = "프로젝트 수정이 완료되었습니다.";
		} else {
			path = "/partner/projectDetail/" + projectNo;
			message = "프로젝트 수정에 실패하였습니다.";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	
	@GetMapping("deleteProject")
	public String deleteProject(@RequestParam("projectNo") int projectNo,
								RedirectAttributes ra) {
		
		int result = service.deleteProject(projectNo);
		
		String path = null;
		String message = null;
		
		if(result > 0) {
			path = "/partner/interiorPortfolioEditMain";
			message = "프로젝트 삭제가 완료되었습니다.";
		} else {
			path = "/partner/projectDetail/" + projectNo;
			message = "프로젝트 삭제에 실패하였습니다.";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	@GetMapping("registMainProject")
	public String registMainProject(@RequestParam("projectNo") int projectNo,
									@SessionAttribute("loginPartnerMember") Partner loginPartnerMember,
									RedirectAttributes ra) {
		
		Portfolio portfolio = service.selectPortfolio(loginPartnerMember.getPortfolioNo());
		
		log.info("확인 : " + portfolio);
		
		int result = service.changeMainProject(portfolio, projectNo);
		String path = null;
		String message = null;
		
		if(result > 0) {
			path = "/partner/interiorPortfolioEditMain";
			message = "메인프로젝트 변경이 완료되었습니다.";
		} else {
			path = "/partner/projectDetail/" + projectNo;
			message = "메인프로젝트 변경에 실패하였습니다.";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
}











