package com.ddukddak.manager.model.service;

import java.util.List;

import com.ddukddak.manager.NLPModel;
import com.ddukddak.manager.model.dto.InquiryCategory;
import com.ddukddak.manager.model.dto.RecommendAnswer;

public interface ChatbotService {


	/** 답변 얻기
	 * @param string 
	 * @param string
	 * @param nlpModel
	 * @return
	 */
	RecommendAnswer recommendAnswer(String category, String inquiry, NLPModel nlpModel);

}
