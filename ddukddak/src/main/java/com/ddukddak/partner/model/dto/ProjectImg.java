package com.ddukddak.partner.model.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProjectImg {
	
	
	private int uploadImgNo;
	private String uploadImgPath;
	private String uploadImgOgName;
	private String uploadImgRename;
	private int uploadImgOrder;
	private int category;
	
//	게시글 불러올때
	private int projectNo;
	private int portfolioNo;
	private String projectName;
	
//	이미지 추가시 멀티파트
	private MultipartFile uploadFile;

}
