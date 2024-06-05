package com.ddukddak.myPage.model.mapper;

import com.ddukddak.member.model.dto.Member;

public interface MemberInfoMapper {

	/** 프로필 이미지 업데이트
	 * @param mem
	 * @return
	 */
	int updateImg(Member mem);


}
