package com.ddukddak.partner.model.service;

import java.util.List;

import com.ddukddak.partner.model.dto.Partner;

public interface InteriorService {

	
	
	/** 시공사 리스트 조회
	 * @return interiorList
	 */
	List<Partner> selectInteriorList();

}
