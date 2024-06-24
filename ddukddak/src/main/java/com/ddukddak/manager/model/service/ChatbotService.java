package com.ddukddak.manager.model.service;

import java.util.List;

import com.ddukddak.manager.NLPModel;
import com.ddukddak.manager.model.dto.InquiryCategory;
import com.ddukddak.manager.model.dto.RecommendAnswer;

public interface ChatbotService {

	/** 문의 카테고리 보여주기
	 * @return
	 */
	List<InquiryCategory> getAllCategory();

	/** 답변 얻기
	 * @param string 
	 * @param string
	 * @param nlpModel
	 * @return
	 */
	RecommendAnswer recommendAnswer(String category, String inquiry, NLPModel nlpModel);

}
