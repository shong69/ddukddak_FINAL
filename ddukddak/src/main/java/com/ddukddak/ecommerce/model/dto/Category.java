package com.ddukddak.ecommerce.model.dto;

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
@Builder
@ToString
public class Category {

	private String bigCategoryNo;
	private String bigCategoryName;
	
	private String categoryNo;
	private String categoryName;
	
}
