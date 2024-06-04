package com.ddukddak.board.model.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BoardImg {

	private int uploadImgNo;
	private String uploadImgPath;
	private String uploadImgOgName;
	private String uploadImgRename;
	private int uploadImgOrder;
	private int category;
	
	private int boardNo;
	private int boardType;
	private String boardName;
	
	private MultipartFile uploadFile;
	
}
