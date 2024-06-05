package com.ddukddak.myPage.model.service;

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

}
