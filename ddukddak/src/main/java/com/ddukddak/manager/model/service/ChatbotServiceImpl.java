package com.ddukddak.manager.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ddukddak.manager.NLPModel;
import com.ddukddak.manager.model.dto.InquiryCategory;
import com.ddukddak.manager.model.dto.RecommendAnswer;
import com.ddukddak.manager.model.mapper.ChatbotMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService{
	
	private final ChatbotMapper mapper;
	private final NLPModel nlp;


	//답변 얻기
	@Override
	public RecommendAnswer recommendAnswer(String categoty, String inquiry, NLPModel nlpModel) {
		String preprocessString =  nlp.categorize(inquiry);
		
		return null;
	}

}
