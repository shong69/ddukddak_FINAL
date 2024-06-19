package com.ddukddak.partner.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.common.util.Utility;
import com.ddukddak.partner.model.ProjectInsertException;
import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.dto.Portfolio;
import com.ddukddak.partner.model.dto.Project;
import com.ddukddak.partner.model.dto.ProjectImg;
import com.ddukddak.partner.model.mapper.InteriorMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor=Exception.class)
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
@Slf4j
public class InteriorServiceImpl implements InteriorService {
	
	private final InteriorMapper mapper;
	
	@Value("${my.interior.web-path}")
	private String webPath;
	
	@Value("${my.interior.folder-path}")
	private String folderPath;
	
	// 시공사 리스트 조회
	@Override
	public List<Partner> selectInteriorList() {
		
		return mapper.selectIneriorList();
	}


	@Override
	public Portfolio selectPortfolio(int portfolioNo) {
		return mapper.selectPortfolio(portfolioNo);
		
	}


	@Override
	public List<Partner> searchInteriorList() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Project selectMainProject(int portfolioNo) {
		return mapper.selectMainProject(portfolioNo);
	}


	@Override
	public List<Project> selectProjectList(int portfolioNo) {
		return mapper.selectProjectList(portfolioNo);
	}


//	@Override
//	public List<ProjectImg> selectImageList(int projectNo) {
//		return mapper.selectImageList(projectNo);
//	}
	// 포트 폴리오 프로젝트 추가
	@Override
	public int insertProject(Project project, List<MultipartFile> imgList) throws IOException {
		
		// 포트 폴리오 데이터 얻어오기
		Portfolio portfolio = new Portfolio();
		log.info("partnerNo : " + project.getPartnerNo());
		
		portfolio = mapper.selectPortfolio(project.getPortfolioNo());
		
		project.setPortfolioNo(portfolio.getPortfolioNo());
		
		int result = mapper.projectInsert(project);
		log.info("projectNo : " + project.getProjectNo());
		
		int result2 = mapper.projectInfoInsert(project);
		
		
		if(result == 0 || result2 == 0) return 0;
		
		List<ProjectImg> uploadList = new ArrayList<>();
		
		if(!imgList.isEmpty()) {
			
			for(int i = 0; i < imgList.size(); i ++) {
				
				String originalName = imgList.get(i).getOriginalFilename();
				String rename = Utility.fileRename(originalName);
				
				ProjectImg img = ProjectImg.builder()
								 .uploadImgOgName(originalName)
								 .uploadImgRename(rename)
								   .uploadImgPath(webPath)
								   .projectNo(project.getProjectNo())
								   .uploadImgOrder(i)
								   .uploadFile(imgList.get(i))
								   .build();
				
				uploadList.add(img);
				
			}
			
		}
		
		if(uploadList.isEmpty()) {
			return project.getProjectNo();
		}
		
		result2 = mapper.insertUploadList(uploadList);
		
		if(result2 == uploadList.size()) {
			
			for(ProjectImg img : uploadList) {
				
				img.getUploadFile().transferTo( new File(folderPath + img.getUploadImgRename()) );
				
			}
			
		} else {
			
			throw new ProjectInsertException("이미지가 정상 삽입되지 않음");
			
		}
		
		return project.getProjectNo();
	}


	// 프로젝트 상세 정보 조회
	@Override
	public Project selectProject(int projectNo) {
		
		return mapper.selectProject(projectNo);
	}

}
























