package com.ddukddak.partner.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.mapper.PartnerMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService{

	private final PartnerMapper mapper;
	
	@Override
	public Partner login(Partner partner) {
		
		return mapper.login(partner);
	}

}
