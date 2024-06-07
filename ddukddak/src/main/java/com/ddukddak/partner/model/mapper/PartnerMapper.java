package com.ddukddak.partner.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.partner.model.dto.Partner;

@Mapper
public interface PartnerMapper {

	Partner login(Partner partner);



}
