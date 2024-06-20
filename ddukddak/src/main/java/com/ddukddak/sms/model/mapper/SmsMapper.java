package com.ddukddak.sms.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SmsMapper {

	/** [SMS 인증] 업데이트 선 시도
	 * @param map
	 * @return
	 */
	int updateSmsAuthKey(Map<String, String> map);

	/** [SMS 인증] 업데이트 실패 시 삽입
	 * @param map
	 * @return
	 */
	int insertSmsAuthKey(Map<String, String> map);

	/** [SMS 인증] 인증키 일치 여부 확인
	 * @param map
	 * @return
	 */
	int checkSmsAuthKey(Map<String, Object> map);
	
}
