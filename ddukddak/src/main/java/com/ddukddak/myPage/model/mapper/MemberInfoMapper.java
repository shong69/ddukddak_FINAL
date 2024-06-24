package com.ddukddak.myPage.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.member.model.dto.Member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.member.model.dto.Member;

@Mapper
public interface MemberInfoMapper {

	/** 프로필 이미지 업데이트
	 * @param mem
	 * @return
	 */
	int updateImg(Member mem);

	/** memberNo에 해당하는 비밀번호 찾기
	 * @param memberNo
	 * @return
	 */
	String selectPw(int memberNo);

	/** 비밀번호 업데이트하기
	 * @param map
	 * @return
	 */
	int changePassword(Map<String, String> map);

	/** 이메일 중복 검사
	 * @param memberEmail
	 * @return
	 */
	int checkEmail(String memberEmail);

	/**이메일 변경
	 * @param map
	 * @return
	 */
	int updateEmail(Map<String, Object> map);

	/**닉네임 변경횟수 조회
	 * @param object
	 * @return
	 */
	int changeCount(Object object);

	/**닉네임 변경
	 * @param map
	 * @return 
	 */
	int updateNickname(Map<String, Object> map);
	
	/**이전 닉네임 조회
	 * @param object
	 * @return
	 */
	String getOldNickname(Object object);

	/** 닉네임 횟수 테이블에 업데이트
	 * @param map
	 */
	void insertNicknameChangeLog(Map<String, Object> map);

	/** 휴대폰 번호 중복 체크
	 * @param phoneNum
	 * @return
	 */
	int checkPhoneNum(String phoneNum);

	/**휴대폰 번호 업데이트
	 * @param map
	 * @return
	 */
	int updatePhoneNum(Map<String, Object> map);

	/** 닉네임 중복 체크
	 * @param object
	 * @return
	 */
	int dupNickname(Object object);

	/** 주소 업데이트
	 * @param map
	 * @return
	 */
	int updateAddr(Map<String, Object> map);




}
