package com.ddukddak.partner.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.partner.model.dto.Partner;

@Mapper
public interface PartnerMapper {

	Partner login(String string);



	/** 파트너 이름, 휴대폰 일치 여부 확인(비동기)
	 * @param partner
	 * @return
	 */
	int partnerNTCheck(Partner partner);

	/** 아이디 찾기 결과(휴대폰)
	 * @param telFindPartner
	 * @return
	 */
	Partner findIdByTel(Partner telFindPartner);
	
}
