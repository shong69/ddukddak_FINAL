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

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
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
		return mapper.checkEmail(memberEmail);
	}
	
}
