package com.ddukddak.ecommerce.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

	private int reviewNo;
	private String reviewContent;
	private String reviewCreateDate;
	private String reviewUpdateDate;
	private int reviewRating;
	private int productNo;
	private int memberNo;
	private String memberId;
	private int orderItemNo;
	private String reviewDelFl;
	
	//리뷰 이미지
	private List<ReviewImg> imgList;

}
