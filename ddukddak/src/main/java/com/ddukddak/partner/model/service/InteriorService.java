package com.ddukddak.partner.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.dto.Portfolio;
import com.ddukddak.partner.model.dto.Project;
import com.ddukddak.partner.model.dto.ProjectImg;

public interface InteriorService {

	
	
	/** 시공사 리스트 조회
	 * @return interiorList
	 */
	List<Partner> selectInteriorList();

//	List<Partner> selectPortfolio(int portfolioNo);

	/** 포트폴리오 조회
	 * @param portfolioNo
	 * @return portfolio
	 */
	Portfolio selectPortfolio(int portfolioNo); 

	List<Partner> searchInteriorList();
	
//	Project selectMainProject(int portfolioNo);

	Map<String, Object> selectProjectList(int portfolioNo); 
	
//	List<ProjectImg> selectImageList (int projectNo);
	
	/** 포트폴리오 프로젝트 등록
	 * @param project
	 * @param imgList
	 * @return projectNo
	 */
	int insertProject(Project project, List<MultipartFile> imgList) throws IOException;

	
	/** 프로젝트 상세 정보
	 * @param projectNo
	 * @return project
	 */
	Project selectProject(int projectNo);


}
