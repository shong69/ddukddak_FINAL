package com.ddukddak.manager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendAnswer {

	private boolean isConfident;
	private String message; // 답변
}
