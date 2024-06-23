package com.ddukddak.manager.model.service;

import java.util.List;

import com.ddukddak.manager.model.dto.InquiryCategory;

public interface ChatbotService {

	/** 문의 카테고리 보여주기
	 * @return
	 */
	List<InquiryCategory> getAllCategory();

}
