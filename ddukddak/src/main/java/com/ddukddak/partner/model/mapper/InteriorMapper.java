package com.ddukddak.partner.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.dto.Portfolio;
import com.ddukddak.partner.model.dto.Project;
import com.ddukddak.partner.model.dto.ProjectImg;

@Mapper
public interface InteriorMapper {

	
	
	/** 시공사 리스트 조회
	 * @return interiorList
	 */
	List<Partner> selectIneriorList();

	Portfolio selectPortfolio(int portfolioNo);
	
	Project selectMainProject(int portfolioNo);

	List<Project> selectProjectList(int portfolioNo);

//	List<ProjectImg> selectImageList(int projectNo);
	
	
//	/** 포트폴리오 데이터 받아오기
//	 * @param project
//	 * @return portfolio
//	 */
//	Portfolio selectPortfolio2(Project project);

	
	/** 포트폴리오 프로젝트 등록
	 * @param project
	 * @return result
	 */
	int projectInsert(Project project);

	
	/** 프로젝트 세부사항 등록
	 * @param project
	 * @return result2
	 */
	int projectInfoInsert(Project project);

	
	/** 프로젝트 이미지 삽입
	 * @param uploadList
	 * @return result2
	 */
	int insertUploadList(List<ProjectImg> uploadList);

	
	
	/** 프로젝트 상세정보 조회
	 * @param projectNo
	 * @return project
	 */
	Project selectProject(int projectNo);

	
	/** 프로젝트 삭제
	 * @param projectNo
	 * @return result
	 */
	int deleteProject(int projectNo);


}
