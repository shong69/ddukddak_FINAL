package com.ddukddak.partner.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.mapper.PartnerMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class PartnerServiceImpl implements PartnerService{

	private final PartnerMapper mapper;
	private final BCryptPasswordEncoder bcrypt;
	
	@Override
	public Partner login(Partner partner) {
		
		Partner loginPartnerMember = mapper.login(partner.getPartnerId());
		
		if(loginPartnerMember == null) return null;
		
		if(!bcrypt.matches(partner.getPartnerPw(), loginPartnerMember.getPartnerPw())) {
			return null;
		}
		
		loginPartnerMember.setPartnerPw(null);
		
		return loginPartnerMember;
	}

	

	/** 파트너 이름, 휴대폰 일치 여부 확인(비동기)
	 *
	 */
	@Override
	public int partnerNTCheck(Partner partner) {

		return mapper.partnerNTCheck(partner);
	}

	/** 아이디 찾기 결과(휴대폰)
	 *
	 */
	@Override
	public Partner findIdByTel(Partner telFindPartner) {
		
		return mapper.findIdByTel(telFindPartner);
	}



	/** 파트너 회원가입 - 사업자등록번호 중복 체크
	 *
	 */
	@Override
	public int checkBusinessNum(String inputBN) {
		
		return mapper.checkBusinessNum(inputBN);
	}



	/** 파트너 회원가입 - 상호명 중복 체크
	 *
	 */
	@Override
	public int checkBusinessName(String inputBName) {
		return mapper.checkBusinessName(inputBName);
	}



	/** 파트너 회원가입 - 아이디 중복 체크
	 *
	 */
	@Override
	public int checkId(String inputId) {
		return mapper.checkId(inputId);
	}



	/** 파트너 회원가입 - 휴대폰 중복 체크
	 *
	 */
	@Override
	public int checkTel(String inputTel) {
		return mapper.checkTel(inputTel);
	}



	/** [파트너 회원가입 제출]
	 *
	 */
	@Override
	public int signup(Partner inputPartner) {
		
		// 비밀번호를 암호화 하여 input member에 세팅
		String encPw = bcrypt.encode(inputPartner.getPartnerPw()); 
		
		inputPartner.setPartnerPw(encPw);
		
		log.info("사업자등록번호 기존 : " + inputPartner.getPartnerBusinessNum());
		
		// 123-12-12345 형식 (-) 제거
        String bsNum = inputPartner.getPartnerBusinessNum().replace("-", "");
        
        log.info("사업자등록번호 형식 변경 : " + bsNum);
        
        // Partner 객체의 사업자 등록번호 업데이트
        inputPartner.setPartnerBusinessNum(bsNum);
		
		return mapper.signup(inputPartner);
	}


//	// 파트너 타입 리스트 조회
//	@Override
//	public List<Map<String, Object>> selectPartnerTypeList() {
//		
//		return mapper.selectPartnerTypeList();
//	}
	
}
