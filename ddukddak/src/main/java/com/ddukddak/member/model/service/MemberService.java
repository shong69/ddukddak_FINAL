package com.ddukddak.member.model.service;

import com.ddukddak.member.model.dto.Member;

public interface MemberService {

	/** [아이디 찾기] 닉네임, 이메일 일치 여부 확인
	 * @param member
	 * @return
	 */
	int memberCheck(Member member);

}
