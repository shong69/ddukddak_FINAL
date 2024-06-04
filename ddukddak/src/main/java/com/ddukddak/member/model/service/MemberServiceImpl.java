package com.ddukddak.member.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.member.model.dto.Member;
import com.ddukddak.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {

	private final MemberMapper mapper;

	/** [아이디 찾기] 닉네임, 이메일 일치 여부 확인
	 *
	 */
	@Override
	public int memberCheck(Member member) {
		
		return mapper.memberCheck(member);
	}
	
	
}
