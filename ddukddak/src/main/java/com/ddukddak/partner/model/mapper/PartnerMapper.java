package com.ddukddak.partner.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.partner.model.dto.Partner;

@Mapper
public interface PartnerMapper {

	Partner login(String string);



	/** 파트너 이름, 휴대폰 일치 여부 확인(비동기)
	 * @param partner
	 * @return
	 */
	int partnerNTCheck(Partner partner);

	/** 아이디 찾기 결과(휴대폰)
	 * @param telFindPartner
	 * @return
	 */
	Partner findIdByTel(Partner telFindPartner);



	/** 파트너 회원가입 - 사업자등록번호 중복 체크
	 * @param inputBN
	 * @return
	 */
	int checkBusinessNum(String inputBN);



	/** 파트너 회원가입 - 상호명 중복 체크
	 * @param inputBName
	 * @return
	 */
	int checkBusinessName(String inputBName);



	/** 파트너 회원가입 - 아이디 중복 체크
	 * @param inputId
	 * @return
	 */
	int checkId(String inputId);



	/** 파트너 회원가입 - 휴대폰 중복 체크
	 * @param inputTel
	 * @return
	 */
	int checkTel(String inputTel);



	/** [파트너 회원가입 제출]
	 * @param inputPatner
	 * @return
	 */
	int signup(Partner inputPartner);



//	/** 파트너 타입 리스트 조회
//	 * @return partnerTypeList
//	 */
//	List<Map<String, Object>> selectPartnerTypeList();
	
}
