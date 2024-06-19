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
public class Portfolio {

	// 포트폴리오
	private int portfolioNo;
	private String portfolioDetail;
	private Project mainProject;
	private List<Project> projectList;
	private String thumbnail;
	private String homeLink;
	
	private String profileImg;
	
	private String partnerBusinessName;
	
	
}
