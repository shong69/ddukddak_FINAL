package com.ddukddak.manager.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ddukddak.member.model.dto.Member;
import com.ddukddak.partner.model.dto.Partner;

@Mapper
public interface ManagerMapper {

	/** 가입 대기 파트너 수 조회
	 * @return
	 */
	int getListCount();

	/** 파트너 가입 대기 목록 조회
	 * @param rowBounds
	 * @return
	 */
	List<Partner> selectPassList(RowBounds rowBounds);

	/** 파트너 승인 업데이트
	 * @param partnerNo
	 * @return
	 */
	int passConfirm(String partnerNo);

	/** 파트너 거절 업데이트
	 * @param partnerNo
	 * @return
	 */
	int passRefuse(String partnerNo);

	/** 회원 수 조회
	 * @return
	 */
	int getMemberListCount();

	/** 회원 목록 조회
	 * @param rowBounds
	 * @return
	 */
	List<Member> selectMemberList(RowBounds rowBounds);

	/** 회원 탈퇴 처리
	 * @param memberNo
	 * @return
	 */
	int memberDelete(String memberNo);

	/** 파트너 회원 수 조회
	 * @return
	 */
	int getPartnerListCount();

	/** 파트너 회원 목록 조회
	 * @param rowBounds
	 * @return
	 */
	List<Partner> selectPartnerList(RowBounds rowBounds);

	/** 파트너 탈퇴 처리
	 * @param partnerNo
	 * @return
	 */
	int partnerDelete(String partnerNo);


}
