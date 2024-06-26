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

	/** 회원가입 - 아이디 중복 체크
	 * @param inputId
	 * @return
	 */
	int checkId(String inputId);

	
	/** 회원가입 - 닉네임 중복 체크
	 * @param inputNickname
	 * @return
	 */
	int checkNickname(String inputNickname);

	/** 회원가입 - 이메일 중복 체크
	 * @param inputEmail
	 * @return
	 */
	int checkEmail(String inputEmail);

	/** 회원가입 - 휴대폰 중복 체크
	 * @param inputTel
	 * @return
	 */
	int checkTel(String inputTel);

	/** [회원가입 제출]
	 * @param inputMember
	 * @param memberAddress
	 * @return
	 */
	int signup(Member inputMember, String[] memberAddr);


	/** 카카오 로그인 - 이메일 가입 멤버 찾기
	 * @param email
	 * @return
	 */
	Member findMemberByKakao(String email);

	/** 카카오 회원가입
	 * @param newKakaoMember
	 * @return
	 */
	Member kakaoSignup(Member newKakaoMember);

	/** 구글 로그인 - 이메일 가입 멤버 찾기
	 * @param email
	 * @return
	 */
	Member findMemberByGoogle(String email);

	/** 구글 회원가입
	 * @param newKakaoMember
	 * @return
	 */
	Member googleSignup(Member newGoogleMember);

	/** 네이버 로그인 - 이메일 멤버 찾기
	 * @param email
	 * @return
	 */
	Member findMemberByNaver(String email);
 
	/** 네이버 회원가입
	 * @param newNaverMember
	 * @return
	 */
	Member naverSignup(Member newNaverMember);



}
