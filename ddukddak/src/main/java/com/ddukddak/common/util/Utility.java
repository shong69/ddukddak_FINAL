package com.ddukddak.common.util;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.extern.slf4j.Slf4j;

public class Utility {

	public static int seqNum = 1; // 1 ~ 99999 반복
	
	public static String fileRename(String originalFileName) {
		// ex) 20240417102705_00004.jpg
		
		// SimpleDateFormat : 시간을 원하는 형태의 문자열로 간단히 변경
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		// new java.util.Date() : 현재 시간을 저장한 자바 객체
		String date = sdf.format(new java.util.Date());
		
		// 00000 포맷
		String number = String.format("%05d", seqNum);
		
		seqNum++;
		
		if(seqNum == 100000) {
			seqNum = 1; 
		}
		
		// 확장자
		// "문자열".subString(인덱스)
		// - 문자열을 인덱스로부터 끝까지 잘라낸 결과를 반환
		
		// "문자열".lastIndexOf(".")
		// - 문자열에서 마지막 "."의 인덱스를 반환
		String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
		
		return date + "_" + number + ext;
	}
	

	/** SMS 난수 생성(String형 숫자 6자리)
	 * @return
	 */
	public static String RandomNumber6() {
        // 숫자만 포함된 6자리 난수를 생성
        return RandomStringUtils.randomNumeric(6);
    }
	
}