package com.ddukddak.common.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {

	/** 비밀번호 찾기 - 아이디 검색(공통)
	 * @param id
	 * @return
	 */
	int idCheck(String id);

	/** 비밀번호 찾기 - 아이디, 전화번호 일치 여부 확인
	 * @param map
	 * @return
	 */
	String commonITCheck(Map<String, String> map);

	/** 기존 비밀번호와 동일 여부 확인
	 * @param inputId
	 * @return
	 */
	String pwCheck(String inputId);
 
	/** 비밀번호 업데이트(회원 선시도)
	 * @param changeMap
	 * @return
	 */
	int changeMemberPw(Map<String, String> changeMap);

	/** 비밀번호 업데이트(파트너)
	 * @param changeMap
	 * @return
	 */
	int changePartnerPw(Map<String, String> changeMap);




}
