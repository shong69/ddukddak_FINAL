package com.ddukddak.email.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {

	/** 인증키 업데이트 먼저 수행
	 * @param map
	 * @return
	 */
	int updateAuthKey(Map<String, String> map);

	/** 업데이트 실패 시 삽입
	 * @param map
	 * @return
	 */
	int insertAuthKey(Map<String, String> map);

	/** 인증번호 일치 여부 확인
	 * @param map
	 * @return
	 */
	int checkAuthKey(Map<String, Object> map);

}
