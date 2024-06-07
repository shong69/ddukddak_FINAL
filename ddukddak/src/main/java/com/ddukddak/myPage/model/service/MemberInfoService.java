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

	/**이메일 업데이트
	 * @param map
	 * @return
	 */
	int updateEmail(Map<String, Object> map);

	/**닉네임 업데이트
	 * @param memberNo
	 * @param memberNickname
	 * @return
	 */
	int updateNickname(Map<String, Object> map);

	/** 휴대폰 번호 중복 체크
	 * @param string
	 * @return
	 */
	int checkPhonNum(String phoneNum);

	/** 휴대폰 번호 업데이트
	 * @param map
	 * @return
	 */
	int updatePhoneNum(Map<String, Object> map);

}
