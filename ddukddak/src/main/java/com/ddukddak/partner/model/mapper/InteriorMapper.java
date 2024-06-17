package com.ddukddak.partner.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.partner.model.dto.Partner;

@Mapper
public interface InteriorMapper {

	
	
	/** 시공사 리스트 조회
	 * @return interiorList
	 */
	List<Partner> selectIneriorList();

}
