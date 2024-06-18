package com.ddukddak.partner.model.service;

import java.util.List;

import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.dto.Project;

public interface InteriorService {

	
	
	/** 시공사 리스트 조회
	 * @return interiorList
	 */
	List<Partner> selectInteriorList();

	List<Partner> selectPortfolio(int portfolioNo);

	List<Partner> searchInteriorList();
	
	List<Project> selectMain(int portfolioNo); 

}
