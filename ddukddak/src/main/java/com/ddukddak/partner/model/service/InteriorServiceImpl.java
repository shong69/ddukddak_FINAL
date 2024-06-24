package com.ddukddak.partner.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.common.util.Utility;
import com.ddukddak.main.model.dto.Pagination;
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
	
	@Value("${my.profile.web-path}")
	private String profileWebPath;
	
	@Value("${my.profile.folder-path}")
	private String profileFolderPath;
	
	// 시공사 리스트 조회
	@Override
	public Map<String, Object> selectInteriorList(int cp) {
		
		int listCount = mapper.getInteriorListCount();
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset,limit);
		
		List<Partner> interiorList = mapper.selectIneriorList(rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("interiorList", interiorList);
		
		return map;
	}

	// 시공사 리스트 검색 조회
	@Override
	public Map<String, Object> searchInteriorList(String query, int cp) {
		
		int listCount = mapper.getSearchCount(query);
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Partner> interiorList = mapper.searchInteriorList(query, rowBounds);
		log.info("인테리어 검색 리스트 : " + interiorList);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("interiorList", interiorList);
		
		return map;
	}

	@Override
	public Portfolio selectPortfolio(int partnerNo) {
		return mapper.selectPortfolio(partnerNo);
		
	}


	@Override
	public List<Partner> searchInteriorList() {
		// TODO Auto-generated method stub
		return null;
	}


//	@Override
//	public Project selectMainProject(int portfolioNo) {
//		return mapper.selectMainProject(portfolioNo);
//	}


	@Override
	public Map<String, Object> selectProjectList(int portfolioNo) {
		
		Project mainProject = mapper.selectMainProject(portfolioNo);
		List<Project> projectList = mapper.selectProjectList(portfolioNo);
		
		
//		log.info("projectList : " + projectList);
		
		for (Project project : projectList) {
			
			Project thumbnailProject = mapper.selectProject(project.getProjectNo());
//			log.info("확인 : " + thumbnailProject.getImgList());
			if(!thumbnailProject.getImgList().isEmpty()) {
				
				project.setImgList(thumbnailProject.getImgList());
			}
			
		}
//		log.info("projectList : " + projectList);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("mainProject", mainProject);
		map.put("projectList", projectList);
		
		return map;
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
		
//		portfolio = mapper.selectPortfolio(project.getPortfolioNo());
//		
//		project.setPortfolioNo(portfolio.getPortfolioNo());
		
		
		
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
		
//		log.info("uploadList : " + uploadList);
		
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
		
//		log.info("selectProject : " + mapper.selectProject(projectNo));
		
		return mapper.selectProject(projectNo);
	}


	// 프로젝트 삭제
	@Override
	public int deleteProject(int projectNo) {
		
		return mapper.deleteProject(projectNo);
	}


	// 프로젝트 수정
	@Override
	public int updateProject(Project project, List<MultipartFile> images) throws IllegalStateException, IOException {
		
		// 프로젝트 내용 수정
		int result = mapper.updateProject(project);
		
		if(result == 0) return 0;
		
		List<ProjectImg> uploadList = new ArrayList<>();
		
		
		for(int i = 0; i < images.size(); i ++) {
			
			log.info("테스트 : " + images.get(i));
			log.info("테스트 : " + images.get(i).isEmpty());
			
			if( !images.get(i).isEmpty() ) {
				log.info("여기가 문제냐");
				String originalName = images.get(i).getOriginalFilename();
				String rename = Utility.fileRename(originalName);
				
				ProjectImg img = ProjectImg.builder()
								 .uploadImgOgName(originalName)
								 .uploadImgRename(rename)
								 .uploadImgPath(webPath)
								 .projectNo(project.getProjectNo())
								 .uploadImgOrder(i)
								 .uploadFile(images.get(i))
								 .build();
				
				uploadList.add(img);
				log.info("i번째 : " + i);
				
				result = mapper.updateImage(img);
				
			}
			
			
		}
		
		// 선택한 파일이 없을 경우
		if(uploadList.isEmpty()) {
			return result;
		}
		
		for(ProjectImg img : uploadList) {
			img.getUploadFile().transferTo( new File(folderPath + img.getUploadImgRename()) );
		}
		
		return result;
	}


	// 메인 프로젝트 변경
	@Override
	public int changeMainProject(Portfolio portfolio, int projectNo) {
		
		List<Project> projectList = new ArrayList<>(portfolio.getProjectList());
//		List<Project> projectList = new ArrayList<>();
//		
//		projectList = mapper.selectProjectListWithoutMainProject( portfolio.getPortfolioNo() ); 
				
				
		int result = -1;
		
		log.info("프로젝트 리스트 : " + projectList);
		
		for(Project project : projectList) {
			
			log.info(project.getMainProjectFl());
			
			if(project.getMainProjectFl().equals("Y") || project.getMainProjectFl() == "Y") {
				log.info("메인 프로젝트 : " + project);
				result = mapper.clearMainProject(project.getProjectNo());
			}
			
		}
		
		result = mapper.registMainProject(projectNo);
		
		return result;
	}

	
	// 시공사 프로필 이미지 변경
	@Override
	public int updateProfileImg(MultipartFile profileImg, Partner loginPartnerMember) throws IOException {
		
		String updatePath = null;
		String rename = null;
		
		if(!profileImg.isEmpty()) {
			rename=Utility.fileRename(profileImg.getOriginalFilename());
			
			updatePath = profileWebPath + rename;
		}
		
		Partner partner = Partner.builder()
						  .partnerNo(loginPartnerMember.getPartnerNo())
						  .profileImg(updatePath)
						  .build();
		
		log.info("partner : " + partner); 
		log.info(updatePath);
		
		int result = mapper.updateProfileImg(partner);
		
		if(result > 0) {
			if(!profileImg.isEmpty()) {
				profileImg.transferTo(new File(profileFolderPath + rename));
			}
			loginPartnerMember.setProfileImg(updatePath);
		}
		
		return result;
	}

	
	// 시공사 홈페이지 변경
	@Override
	public int updateHomeLink(Partner partner) {
		
		return mapper.updateHomeLink(partner);
	}

	// 포트폴리오 생성
	@Override
	public int insertPortfolio(int partnerNo) {
		
		return mapper.insertPortfolio(partnerNo);
	}

	// 포트폴리오 번호 조회
	@Override
	public int selectPortfolioNo(int partnerNo) {
		
		return mapper.selectPortfolioNo(partnerNo);
	}

	@Override
	public Portfolio selectPortfolio2(int portfolioNo) {
		
		return mapper.selectPortfolio2(portfolioNo);
	}




	


}
























