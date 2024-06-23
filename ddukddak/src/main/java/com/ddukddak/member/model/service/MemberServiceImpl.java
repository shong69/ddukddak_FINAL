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


	/** 회원가입 - 아이디 중복 체크
	 *
	 */
	@Override
	public int checkId(String inputId) {
		
		return mapper.checkId(inputId);
	}


	/** 회원가입 - 닉네임 중복 체크
	 *
	 */
	@Override
	public int checkNickname(String inputNickname) {
		return mapper.checkNickname(inputNickname);
	}


	/** 회원가입 - 이메일 중복 체크
	 *
	 */
	@Override
	public int checkEmail(String inputEmail) {
		return mapper.checkEmail(inputEmail);
	}


	/** 회원가입 - 휴대폰 중복 체크
	 *
	 */
	@Override
	public int checkTel(String inputTel) {
		return mapper.checkTel(inputTel);
	}


	/** [회원가입 제출]
	 *
	 */
	@Override
	public int signup(Member inputMember, String[] memberAddr) {
		
		if(!inputMember.getMemberAddr().equals(",,")) {
			
			String address = String.join("^^^", memberAddr);
			
			// inputMember 주소로 합쳐진 주소를 세팅 
			inputMember.setMemberAddr(address);
			
			
		} else { // 주소 입력 X
			
			inputMember.setMemberAddr(null); // null 저장
		}
		
		// 비밀번호를 암호화 하여 input member에 세팅
		String encPw = bcrypt.encode(inputMember.getMemberPw()); 
		
		inputMember.setMemberPw(encPw);
		
		return mapper.signup(inputMember);
	}


	/** 네이버 로그인 - 이메일로 가입한 멤버 찾기
	 *
	 */
	@Override
	public Member findMemberByEmail(String naverEmail) {
		
		return mapper.findMemberByEmail(naverEmail);
	}


	/** 네이버 회원가입
	 *
	 */
	@Override
	public Member naverSignup(Member newNaverMember) {
		
		// 비밀번호를 암호화
		String encPw = bcrypt.encode(newNaverMember.getMemberPw()); 
		
		// 암호화 세팅
		newNaverMember.setMemberPw(encPw);
		
		// 네이버 정보 업데이트하기
		int result = mapper.naverSignup(newNaverMember);
		
		// 반환해줄 멤버 객체 생성
		Member signinMember = new Member();
		
		if(result > 0) {
			
			// 넣었던 newNaverMember의 이메일로 다시 멤버 찾아서 셀렉트해줌
			signinMember = mapper.findMemberByEmail(newNaverMember.getMemberEmail());
			
		} else {
			
			return null;
		}
		
		
		// 다시 찾아준 멤버 반환 -> 회원가입 후 로그인 시 정보 바로 적용되게
		return signinMember;
	}


	/** 카카오 중복 찾기
	 *
	 */
	@Override
	public Member findMemberByKakao(String email) {
		// TODO Auto-generated method stub
		return mapper.findMemberByKakao(email);
	}


	/** 카카오 회원가입
	 *
	 */
	@Override
	public Member kakaoSignup(Member newKakaoMember) {
		
		// 비밀번호를 암호화
		String encPw = bcrypt.encode(newKakaoMember.getMemberPw()); 
		
		// 암호화 세팅
		newKakaoMember.setMemberPw(encPw);
		
		// 카카오 정보 업데이트하기
		int result = mapper.kakaoSignup(newKakaoMember);
		
		// 반환해줄 멤버 객체 생성
		Member signinMember = new Member();
		
		if(result > 0) {
			
			// 넣었던 newNaverMember의 이메일로 다시 멤버 찾아서 셀렉트해줌
			signinMember = mapper.findMemberByKakao(newKakaoMember.getMemberEmail());
			
		} else {
			
			return null;
		}
		
		
		return signinMember;
	}


	// 구글 중복 찾기
	@Override
	public Member findMemberByGoogle(String email) {
		// TODO Auto-generated method stub
		return mapper.findMemberByGoogle(email);
	}


	// 구글 회원가입
	@Override
	public Member googleSignup(Member newGoogleMember) {

		// 비밀번호를 암호화
		String encPw = bcrypt.encode(newGoogleMember.getMemberPw()); 
		
		// 암호화 세팅
		newGoogleMember.setMemberPw(encPw);
		
		// 구글 정보 업데이트하기
		int result = mapper.googleSignup(newGoogleMember);
		
		// 반환해줄 멤버 객체 생성
		Member signinMember = new Member();
		
		if(result > 0) {
			
			// 넣었던 newNaverMember의 이메일로 다시 멤버 찾아서 셀렉트해줌
			signinMember = mapper.findMemberByGoogle(newGoogleMember.getMemberEmail());
			
		} else {
			
			return null;
		}
		
		
		return signinMember;
	}


	
	
}
