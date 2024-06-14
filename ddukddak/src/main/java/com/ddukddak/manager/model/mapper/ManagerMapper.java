package com.ddukddak.manager.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.ddukddak.partner.model.dto.Partner;

@Mapper
public interface ManagerMapper {

	/** 가입 대기 파트너 수 조회
	 * @return
	 */
	int getListCount();

	/** 파트너 가입 대기 목록 조회
	 * @param rowBounds
	 * @return
	 */
	List<Partner> selectPassList(RowBounds rowBounds);

}
