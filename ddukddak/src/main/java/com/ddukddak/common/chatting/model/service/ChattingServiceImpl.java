package com.ddukddak.common.chatting.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.common.chatting.model.dto.ChattingRoom;
import com.ddukddak.common.chatting.model.dto.Message;
import com.ddukddak.common.chatting.model.mapper.ChattingMapper;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.partner.model.dto.Partner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChattingServiceImpl implements ChattingService{
	
	private final ChattingMapper mapper;
	
	//채팅 목록 조회
	@Override
	public List<ChattingRoom> selectRoomList( Map<String, Object> map) {
		//isMember : "MEMBER" 혹은 "PARTNER"로 들어온다
		return mapper.selectRoomList(map);
	}


	
	//채팅방 번호 체크(기존 존재 여부)
	@Override
	public int checkChattingNo(Map<String, Integer> map) {
		return mapper.checkChattingNo(map);
	}

	//새로운 채팅방 생성
	@Override
	public int createChattingRoom(Map<String, Integer> map) {
		int result = mapper.createChattingRoom(map);
		
		if(result >0 ) {
			return (int)map.get("chattingNo");
			
		}
		return 0;
	}


	//메시지 기록 불러오기
	@Override
	public List<Message> selectMessageList(Map<String, Object> paramMap) {
		log.info("{}", paramMap.get("chattingNo"));
		
		log.info("메시지 리스트{}",mapper.selectMessageList(paramMap));
		List<Message> list = mapper.selectMessageList(paramMap);
		
		if(!list.isEmpty()) {
			int result = mapper.updateReadFlag(paramMap);
			log.info("메시지 읽음 처리1 :{}",result);
		}
		
		return list;
	}
	
	//채팅 상대 검색
	@Override
	public List<Partner> selectTarget(Map<String, Object> map) {
		log.info("채팅상대검색:{}:",mapper.selectTarget(map));
		return mapper.selectTarget(map);
	}


	//메시지 보내기
	@Override
	public int insertMessage(Message msg) {
		log.info("메시지 내용{}",msg);
		return mapper.insertMessage(msg);
	}

	@Override
	public int updateReadFlag(Map<String, Object> paramMap) {
		log.info("메시지 읽음 처리2:{}",mapper.updateReadFlag(paramMap));
		return mapper.updateReadFlag(paramMap);
	}


	/*시공사*/
	
	//[시공사]채팅 상대 검색
	@Override
	public List<Member> IselectTarget(Map<String, Object> map) {
		log.info("채팅상대검색:{}:",mapper.IselectTarget(map));
		return mapper.IselectTarget(map);
	}

	//[시공사]채팅방 검색
	@Override
	public int IcheckChattingNo(Map<String, Integer> map) {
		return mapper.IcheckChattingNo(map);
	}

	//[시공사]새로운 채팅방 생성
	@Override
	public int IcreateChattingRoom(Map<String, Integer> map) {
		int result = mapper.IcreateChattingRoom(map);
		
		if(result >0 ) {
			log.info("채팅방 번호 : {}",(int)map.get("chattingNo"));
			return (int)map.get("chattingNo");
			
		}
		return 0;
	}


	//[시공사]메시지 기록 불러오기
	@Override
	public List<Message> IselectMessageList(Map<String, Object> paramMap) {
		log.info("채팅기록 불러오기 채팅방 번호 : {}", paramMap.get("chattingNo"));
		
		log.info("메시지 리스트{}",mapper.selectMessageList(paramMap));
		List<Message> list = mapper.selectMessageList(paramMap);
		
		if(!list.isEmpty()) {
			int result = mapper.IupdateReadFlag(paramMap);
			log.info("시공사 메시지 읽음 처리1 :{}",mapper.IupdateReadFlag(paramMap));
		}
		
		return list;
	}



	//[시공사]읽음 표시 업데이트
	@Override
	public int IupdateReadFlag(Map<String, Object> paramMap) {
		log.info("시공사 메시지 읽음 처리2 :{}",mapper.IupdateReadFlag(paramMap));
		return mapper.IupdateReadFlag(paramMap);
	}





	
}
