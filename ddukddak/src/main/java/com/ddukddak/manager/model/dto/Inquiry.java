package com.ddukddak.manager.model.dto;

import lombok.Data;

@Data
public class Inquiry {

	private long inquiryNo;
	private long CategoryNo;
	private String inquiryName;
	
	private String CategoryName;
}
