package com.ddukddak.myPage.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.member.model.dto.Member;

@Mapper
public interface MemberInfoMapper {

	/** 프로필 이미지 업데이트
	 * @param mem
	 * @return
	 */
	int updateImg(Member mem);


}
