package com.ddukddak.partner.model.dto;

import java.util.List;

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
public class Project {

	// 파트너
	private int partnerNo;
	
	private int projectNo;
	private String projectName;
	private String housingType;
	private String workForm;
	private Number workArea;
	private Number constructionCost;
	private String region;
	private String constructionYear;
	private String familySize;
	private String mainProjectFl;
	private String  projectContent;
	private List<ProjectImg> imgList;
	private int portfolioNo;
	
	// 포트폴리오
	private String thumbnail;
	private String homeLink;
	
	private String profileImg;
	
	private String partnerBusinessName;
}
