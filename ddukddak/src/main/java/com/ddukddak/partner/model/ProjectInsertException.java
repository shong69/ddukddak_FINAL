package com.ddukddak.partner.model;

public class ProjectInsertException extends RuntimeException {

	public ProjectInsertException() {
		super("이미지 삽입 중 예외 발생");
	}
	
	public ProjectInsertException(String message) {
		super(message);
	}
	
}
