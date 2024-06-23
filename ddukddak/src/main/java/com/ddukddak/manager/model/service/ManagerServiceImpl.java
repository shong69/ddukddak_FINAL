package com.ddukddak.manager.model.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.board.model.dto.Report;
import com.ddukddak.main.model.dto.Pagination;
import com.ddukddak.manager.model.mapper.ManagerMapper;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.sms.model.service.SmsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerServiceImpl implements ManagerService {

	private final ManagerMapper mapper;
	
	
	/** 파트너 가입 승인 관리 - 목록 조회
	 * 
	 */
	@Override
	public Map<String, Object> selectPassList(int cp) {
		
		// 가입 대기 중인 파트너 수 조회
		int listCount = mapper.getListCount();
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Partner> passList = mapper.selectPassList(rowBounds);
		
		// 목록 + 페이지네이션
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("passList", passList);
		
		return map;
	}
	
	
	/** 파트너 가입 승인
	 *
	 */
	@Override
	public int passConfirm(String partnerNo) {
		return mapper.passConfirm(partnerNo);
	}


	/** 파트너 가입 거절
	 *
	 */
	@Override
	public int passRefuse(String partnerNo) {
		return mapper.passRefuse(partnerNo);
	}


	/** 파트너 다중 승인
	 *
	 */
	@Override
	public int updateMultiPass(String action, List<Map<String, String>> partners) {
		
		// 업데이트 누적 개수 추가할 변수
		int updateResult = 0;
		
	    for (Map<String, String> partner : partners) {
	        
	    	String partnerNo = partner.get("partnerNo");	    	
	        
	    	if(action.equals("confirm")) {
	    		
	    		// 업데이트 될 때마다 변수에 누적 개수 반환
	    		updateResult += mapper.passConfirm(partnerNo);
	    		
	    		
	    		
	    	} else if (action.equals("refuse")) {
	    		
	    		// 업데이트 될 때마다 변수에 누적 개수 반환
	            updateResult += mapper.passRefuse(partnerNo);
	            
	        } else {
	        	
	        	return 0;
	        }
	    }
	          
     
        log.info("updateResult : " + updateResult);
            

        return updateResult;
	}


	// 회원 관리 - 회원 목록 조회
	@Override
	public Map<String, Object> selectMember(int cp) {
		// 가입 대기 중인 파트너 수 조회
		int listCount = mapper.getMemberListCount();
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Member> memberList = mapper.selectMemberList(rowBounds);
		
		// 목록 + 페이지네이션
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("memberList", memberList);
		
		return map;
	}


	// 회원 탈퇴 처리
	@Override
	public int memberDelete(String memberNo) {
		return mapper.memberDelete(memberNo);
	}
	
	// 회원 다중 탈퇴 처리
		@Override
		public int memberMultiDelete(String action, List<Map<String, String>> members) {
			// 업데이트 누적 개수 추가할 변수
					int updateResult = 0;
					
				    for (Map<String, String> member : members) {
				        
				    	String memberNo = member.get("memberNo");	    	
				        
				    	if(action.equals("delete")) {
				    		
				    		// 업데이트 될 때마다 변수에 누적 개수 반환
				    		updateResult += mapper.memberDelete(memberNo);
				    		
				    		
				    		
				    	} else {
				        	
				        	return 0;
				        }
				    }
				          
			     
			        log.info("updateResult : " + updateResult);
			            

			        return updateResult;
		}


	// 파트너 관리 - 파트너 목록 조회
	@Override
	public Map<String, Object> selectPartner(int cp) {
		// 가입 대기 중인 파트너 수 조회
				int listCount = mapper.getPartnerListCount();
				
				Pagination pagination = new Pagination(cp, listCount);
				
				int limit = pagination.getLimit();
				int offset = (cp - 1) * limit;
				RowBounds rowBounds = new RowBounds(offset, limit);
				
				List<Partner> partnerList = mapper.selectPartnerList(rowBounds);
				
				// 목록 + 페이지네이션
				Map<String, Object> map = new HashMap<>();
				
				map.put("pagination", pagination);
				map.put("partnerList", partnerList);
				
		return map;
	}


	// 파트너 탈퇴 처리
	@Override
	public int partnerDelete(String partnerNo) {
		return mapper.partnerDelete(partnerNo);
	}


	// 파트너 다중 탈퇴 처리
	@Override
	public int partnerMultiDelete(String action, List<Map<String, String>> partners) {
		// 업데이트 누적 개수 추가할 변수
				int updateResult = 0;
				
			    for (Map<String, String> partner : partners) {
			        
			    	String partnerNo = partner.get("partnerNo");	    	
			        
			    	if(action.equals("delete")) {
			    		
			    		// 업데이트 될 때마다 변수에 누적 개수 반환
			    		updateResult += mapper.partnerDelete(partnerNo);
			    		
			    		
			    		
			    	} else {
			        	
			        	return 0;
			        }
			    }
			          
		     
		        log.info("updateResult : " + updateResult);
		            

		        return updateResult;
	}


	// 신고관리
	@Override
	public Map<String, Object> report(int cp) {
		// 가입 대기 중인 파트너 수 조회
		int listCount = mapper.getReportCount();
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Report> reportList = mapper.selectReportList(rowBounds);
		
		// 목록 + 페이지네이션
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("reportList", reportList);
		
		return map;
	}


	
}
