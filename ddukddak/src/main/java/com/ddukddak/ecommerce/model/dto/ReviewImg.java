package com.ddukddak.ecommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewImg {
	//리뷰 이미지
	private int reviewNo;
	private int uploadImgNo;
	private String uploadImgPath;
	private String uploadImgOgName;
	private String uploadImgRename;
}
