package com.ddukddak.manager.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ddukddak.manager.NLPModel;
import com.ddukddak.manager.model.dto.Inquiry;
import com.ddukddak.manager.model.dto.InquiryCategory;
import com.ddukddak.manager.model.dto.RecommendAnswer;
import com.ddukddak.manager.model.mapper.ChatbotMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatbotServiceImpl implements ChatbotService{
	
	private final ChatbotMapper mapper;
	private final NLPModel nlp;


	//답변 얻기
	@Override
	public RecommendAnswer recommendAnswer(String categoty, String inquiry, NLPModel nlpModel) {
		String predictedCategory =  nlp.categorize(inquiry);
		log.info("예측된 카테고리 : " + predictedCategory);
		
		Inquiry inquiryData = mapper.findInquiryByCategoryAndQuestion(predictedCategory, inquiry);
		
		if(inquiryData != null) {
			return new RecommendAnswer(true,  "답변을 찾았습니다.", inquiryData.getCategoryName());
			
		} else {
			return new RecommendAnswer(false,  "답변을 못찾았습니다.", inquiryData.getCategoryName());
		}
		
	}

}
