package com.ddukddak.manager.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.manager.model.dto.Inquiry;

@Mapper
public interface ChatbotMapper {

	Inquiry findInquiryByCategoryAndQuestion(String predictedCategory, String inquiry);

}
