package com.ddukddak.myPage.model.service;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.common.util.Utility;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.myPage.model.mapper.MemberInfoMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
@Slf4j
public class MemberInfoServiceImpl implements MemberInfoService{
	
	private final MemberInfoMapper mapper;
	private final BCryptPasswordEncoder bcrypt;
	
	@Value("${my.profile.web-path}")
	private String profileWebPath;
	
	@Value("${my.profile.folder-path}")
	private String profileFolderPath;

	//프로필 이미지 변경
	@Override
	public int updateImg(MultipartFile profileImg, Member loginMember) throws Exception {
		
		String updatePath = null;
		String rename = null;
		
		if(!profileImg.isEmpty()) {
			//1.파일명 rename
			rename = Utility.fileRename(profileImg.getOriginalFilename());

			
			//서버저장위치
			updatePath = profileWebPath + rename;
			
		}
		
		//수정된 이미지 + 회원 정보를 dto에 저장
		Member mem = Member.builder()
				.memberNo(loginMember.getMemberNo())
				.profileImg(updatePath)
				.build();
		
		int result = mapper.updateImg(mem);
		
		if(result>0) { //DB 수정 성공
			if(!profileImg.isEmpty()) { //업로드 이미지 O
				//pc 저장 경로
				profileImg.transferTo(new File(profileFolderPath + rename));
			}
			loginMember.setProfileImg(updatePath);//업데이트한 이미지 서버저장경로로 바꾸기
		}
		
		return result;
	}

	@Override
	public int changePassword(Map<String, String> map, int memberNo) {
		//현재 비밀번호 확인
		String originPw = mapper.selectPw(memberNo);
		log.info(originPw);
		log.info( map.get("newPw"));
		log.info(map.get("currentPw"));
		log.info( bcrypt.encode(map.get("currentPw")));
		
		if(!bcrypt.matches(map.get("currentPw"), originPw)) {
			return 0;
		}
		//새 비밀번호 등록하기
		
		String encPw = bcrypt.encode(map.get("newPw"));
		
		map.put("encPw", encPw);
		map.put("memberNo", String.valueOf(memberNo));
		return mapper.changePassword(map);
	}

	//이메일 중복 검사
	@Override
	public int checkEmail(String memberEmail) {
		log.info("memberEmail {}", memberEmail);
		int result = mapper.checkEmail(memberEmail);
		log.info("result {}", result);
		return result;
	}

	//이메일 업데이트
	@Override
	public int updateEmail(Map<String, Object> map) {

		return mapper.updateEmail(map);
	}

	//닉네임 업데이트
	@Override
	public int updateNickname(Map<String, Object> map) {
		int result = 0;
		
		int dupNickname = mapper.dupNickname(map.get("memberNickname"));
		
		if(dupNickname>0) {
			log.info("중복닉넴",1);
			result = -2;
			return result;
		}
		
		//닉네임 변경횟수 조회
		int changeCount = mapper.changeCount(map.get("memberNo"));
		
		//한 달 내 이미 4번 변경했을 때
		if(changeCount >= 4) {
			log.debug("변경횟수 : "+changeCount);
			result= -1;
			return result;
		}	
		

		//닉네임 업데이트		
		int updateRows = mapper.updateNickname(map); //닉네임 업데이트
		
		if(updateRows != 0) {
			mapper.insertNicknameChangeLog(map); //닉네임 로그 테이블 insert
			log.info("닉넴 변경");
			result = changeCount +1;
			
		}
		return result;
	}

	//휴대폰 번호 중복 체크
	@Override
	public int checkPhonNum(String phoneNum) {
		return mapper.checkPhoneNum(phoneNum);
	}

	//휴대폰 번호 업데이트
	@Override
	public int updatePhoneNum(Map<String, Object> map) {
		//updatePhoneNum, memberNo
		return mapper.updatePhoneNum(map);
	}

	// 주소 업데이트
	@Override
	public int updateAddress(Map<String, Object> map) {
	    // 주소 합치기
	    String postcode = (String) map.get("postcode");
	    String address = (String) map.get("address");
	    String detailAddress = (String) map.get("detailAddress");
	    
	    String memberAddr = postcode + " " + address + " " + detailAddress;
	    log.info("주소 형태 확인: {}", memberAddr);
	    
	    map.put("memberAddr", memberAddr);
	    
	    int result = mapper.updateAddr(map);
	    
	    return result;
	}


	

	
}
