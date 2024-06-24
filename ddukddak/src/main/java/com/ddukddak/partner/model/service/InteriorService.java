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
	Map<String, Object> selectInteriorList(int cp);

//	List<Partner> selectPortfolio(int portfolioNo);

	
	/** 시공사 검색 리스트 조회
	 * @param query
	 * @param cp
	 * @return interiorList
	 */
	Map<String, Object> searchInteriorList(String query, int cp);
	
	
	/** 포트폴리오 조회
	 * @param portfolioNo
	 * @return portfolio
	 */
	Portfolio selectPortfolio(int partnerNo); 

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

	
	/** 프로젝트 삭제
	 * @param projectNo
	 * @return result
	 */
	int deleteProject(int projectNo);

	
	
	/** 프로젝트 수정
 	 * @param project
	 * @param images
	 * @param deleteOrder
	 * @return result
	 */
	int updateProject(Project project, List<MultipartFile> images) throws IllegalStateException, IOException;

	
	/** 메인 프로젝트 변경
	 * @param portfolio
	 * @param projectNo
	 * @return
	 */
	int changeMainProject(Portfolio portfolio, int projectNo);

	
	
	/** 시공사 프로필 이미지 	변경
	 * @param profileImg
	 * @param loginPartnerMember
	 * @return result
	 */
	int updateProfileImg(MultipartFile profileImg, Partner loginPartnerMember) throws IOException;

	
	/** 시공사 홈페이지 주소 등록
	 * @param partner
	 * @return result
	 */
	int updateHomeLink(Partner partner);

	
	/** 포트폴리오 생성
	 * @param partnerNo
	 * @return result
	 */
	int insertPortfolio(int partnerNo);

	
	
	/** 포트폴리오 번호 조회
	 * @param partnerNo
	 * @return portfolioNo
	 */
	int selectPortfolioNo(int partnerNo);

	Portfolio selectPortfolio2(int portfolioNo);


	
	
	


}
