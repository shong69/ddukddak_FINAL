package com.ddukddak.member.model.service;

import com.ddukddak.member.model.dto.Member;

public interface MemberService {

	/** [메일] 아이디 찾기 / 이름, 이메일 일치 여부 확인
	 * @param member
	 * @return
	 */
	int memberNMCheck(Member member);

	/** [아이디 찾기 결과] 이메일로 아이디 찾기
	 * @param memberEmail
	 * @return
	 */
	Member findIdByEmail(Member member);

	/** [아이디 찾기 결과] 휴대폰으로 아이디 찾기
	 * @param memberTel
	 * @return
	 */
	Member findIdByTel(Member member);

	/** [SMS] 아이디 찾기 / 이름, 휴대폰 일치 여부 확인 
	 * @param member
	 * @return
	 */
	int memberNTCheck(Member member);

	Member login(Member member);



}
