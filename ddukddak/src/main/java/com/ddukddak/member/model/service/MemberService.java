package com.ddukddak.member.model.service;

import com.ddukddak.member.model.dto.Member;

public interface MemberService {

	/** [아이디 찾기] 닉네임, 이메일 일치 여부 확인
	 * @param member
	 * @return
	 */
	int memberNMCheck(Member member);

	/** 이메일로 아이디 찾기
	 * @param memberEmail
	 * @return
	 */
	Member findIdByEmail(Member member);

	/** 휴대폰으로 아이디 찾기
	 * @param memberTel
	 * @return
	 */
	Member findIdByTel(Member member);



}
