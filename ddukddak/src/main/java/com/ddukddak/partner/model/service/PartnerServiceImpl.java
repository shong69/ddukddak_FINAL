package com.ddukddak.partner.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.partner.dto.Partner;
import com.ddukddak.partner.model.mapper.PartnerMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {
	
	private final PartnerMapper mapper;

	/** 파트너 이름, 휴대폰 일치 여부 확인(비동기)
	 *
	 */
	@Override
	public int partnerNTCheck(Partner partner) {

		return mapper.partnerNTCheck(partner);
	}

	/** 아이디 찾기 결과(휴대폰)
	 *
	 */
	@Override
	public Partner findIdByTel(Partner telFindPartner) {
		
		return mapper.findIdByTel(telFindPartner);
	}
	
}
