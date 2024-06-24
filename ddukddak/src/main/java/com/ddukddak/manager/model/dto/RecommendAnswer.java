package com.ddukddak.manager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendAnswer {

	private boolean isConfident;
	private String message;
	private String answer; // 답변
}
