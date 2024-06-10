package com.ddukddak.member.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.member.model.dto.Member;
import com.ddukddak.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {

	private final MemberMapper mapper;
	private final BCryptPasswordEncoder bcrypt;

	/** [메일] 아이디 찾기 / 이름, 이메일 일치 여부 확인
	 *
	 */
	@Override
	public int memberNMCheck(Member member) {
		
		return mapper.memberNMCheck(member);
	}

	
	/** [아이디 찾기 결과] 이메일로 아이디 찾기
	 *
	 */
	@Override
	public Member findIdByEmail(Member member) {
		
		return mapper.findIdByEmail(member);
	}


	/** [아이디 찾기 결과] 휴대폰으로 아이디 찾기
	 *
	 */
	@Override
	public Member findIdByTel(Member member) {
		
		return mapper.findIdByTel(member);
	}


	/** [SMS] 아이디 찾기 / 이름, 이메일 일치 여부 확인
	 *
	 */
	@Override
	public int memberNTCheck(Member member) {
		
		return mapper.memberNTCheck(member);
	}


	@Override
	public Member login(Member member) {
		Member loginMember = mapper.login(member.getMemberId());
		if(loginMember == null) return null;
		
		if(!bcrypt.matches(member.getMemberPw(), loginMember.getMemberPw())) {
			return null;
		}
		loginMember.setMemberPw(null);
		
		
		return loginMember;
	}


	
	
}
