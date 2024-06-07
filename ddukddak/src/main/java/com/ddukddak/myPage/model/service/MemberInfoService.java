package com.ddukddak.myPage.model.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.member.model.dto.Member;

public interface MemberInfoService {

	/**프로필 이미지 변경
	 * @param profileImg
	 * @param loginMember
	 * @return
	 * @throws Exception 
	 */
	int updateImg(MultipartFile profileImg, Member loginMember) throws Exception;

	/** 비밀번호 변경
	 * @param map
	 * @param memberNo
	 * @return
	 */
	int changePassword(Map<String, String> map, int memberNo);

	/**이메일 중복 검사
	 * @param memberEmail
	 * @return
	 */
	int checkEmail(String memberEmail);

}
