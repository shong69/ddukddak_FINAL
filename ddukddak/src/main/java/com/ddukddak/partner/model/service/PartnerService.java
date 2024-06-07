package com.ddukddak.partner.model.service;

import com.ddukddak.partner.dto.Partner;

public interface PartnerService {

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
