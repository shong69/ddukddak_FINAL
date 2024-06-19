package com.ddukddak.partner.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.dto.Project;

@Mapper
public interface InteriorMapper {

	
	
	/** 시공사 리스트 조회
	 * @return interiorList
	 */
	List<Partner> selectIneriorList();

	List<Partner> selectPortfolio(int portfolioNo);
	
	List<Project> selectMain(int portfolioNo);

	
	/** 포트폴리오 프로젝트 등록
	 * @param project
	 * @return result
	 */
	int projectInsert(Project project);

}
