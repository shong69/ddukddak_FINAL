package com.ddukddak.partner.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.dto.Project;
import com.ddukddak.partner.model.dto.ProjectImg;

public interface InteriorService {

	
	
	/** 시공사 리스트 조회
	 * @return interiorList
	 */
	List<Partner> selectInteriorList();

	List<Partner> selectPortfolio(int portfolioNo);

	List<Partner> searchInteriorList();
	
	List<Project> selectMain(int portfolioNo);

	List<Project> selectPortfolioList(int portfolioNo); 
	
	List<ProjectImg> selectImageList (int projectNo);
	
	/** 포트폴리오 프로젝트 등록
	 * @param project
	 * @param imgList
	 * @return projectNo
	 */
	int insertProject(Project project, List<MultipartFile> imgList); 

}
