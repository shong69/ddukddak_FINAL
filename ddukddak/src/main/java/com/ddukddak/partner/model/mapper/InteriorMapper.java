package com.ddukddak.partner.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

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
	
	
	/** 시공사 리스트 검색 조회
	 * @param query
	 * @param cp
	 * @return interiorList
	 */
	List<Partner> searchInteriorList(String query, RowBounds rowBounds);
	

	Portfolio selectPortfolio(int partnerNo);
	
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

	
	
	/** 프로젝트 내용 수정
	 * @param project
	 * @return result
	 */
	int updateProject(Project project);

	
//	/** 프로젝트 이미지 삭제
//	 * @param map
//	 * @return
//	 */
//	int deleteImage(Map<String, Object> map);

	
	/** 프로젝트 이미지 수정
	 * @param img
	 * @return result
	 */
	int updateImage(ProjectImg img);

	
	/** 메인프로젝트 해제
	 * @param projectNo
	 * @return result
	 */
	int clearMainProject(int projectNo);

	
	/** 메인 프로젝트 변경
	 * @param projectNo
	 * @return result
	 */
	int registMainProject(int projectNo);


	
	/** 시공사 개수 조회
	 * @return listCount
	 */
	int getInteriorListCount();


	/** 검색 시공사 개수 조회
	 * @param query
	 * @return listCount
	 */
	int getSearchCount(String query);


	/** 시공사 프로필 이미지 변경
	 * @param partner
	 * @return result
	 */
	int updateProfileImg(Partner partner);


	
	/** 시공사 홈페이지 변경
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




	
//	/** 메인 프로젝트 제외한 프로젝트 리스트 조회
//	 * @param portfolioNo
//	 * @return projectList
//	 */
//	List<Project> selectProjectListWithoutMainProject(int portfolioNo);

	

	
//	/** 프로젝트 이미지 삽입(1행)
//	 * @param img
//	 * @return result
//	 */
//	int insertImage(ProjectImg img);


}
