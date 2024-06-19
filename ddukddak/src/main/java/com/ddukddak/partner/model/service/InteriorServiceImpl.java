package com.ddukddak.partner.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.dto.Project;
import com.ddukddak.partner.model.dto.ProjectImg;
import com.ddukddak.partner.model.mapper.InteriorMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InteriorServiceImpl implements InteriorService {
	
	private final InteriorMapper mapper;
	
	
	// 시공사 리스트 조회
	@Override
	public List<Partner> selectInteriorList() {
		
		return mapper.selectIneriorList();
	}


	@Override
	public List<Partner> selectPortfolio(int portfolioNo) {
		return mapper.selectPortfolio(portfolioNo);
		
	}


	@Override
	public List<Partner> searchInteriorList() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Project> selectMain(int portfolioNo) {
		return mapper.selectMain(portfolioNo);
	}


	@Override
	public List<Project> selectPortfolioList(int portfolioNo) {
		return mapper.selectPortfolioList(portfolioNo);
	}


	@Override
	public List<ProjectImg> selectImageList(int projectNo) {
		return mapper.selectImageList(projectNo);
	// 포트 폴리오 프로젝트 추가
	@Override
	public int insertProject(Project project, List<MultipartFile> imgList) {
		
		int result = mapper.projectInsert(project);
//		int result2 = mapper.projectInfoInsert(project);
		
		
		if(result == 0) return 0;
		
		return 0;
	}

}
























