package com.ddukddak.common.chatting.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddukddak.common.chatting.model.dto.ChattingRoom;
import com.ddukddak.common.chatting.model.dto.Message;
import com.ddukddak.common.chatting.model.mapper.ChattingMapper;
import com.ddukddak.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingServiceImpl implements ChattingService{
	
	private final ChattingMapper mapper;
	
	//채팅방 목록 조회
	@Override
	public List<ChattingRoom> selectRoomList(int memberNo) {
		return mapper.selectRoomList(memberNo);
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
	public List<Message> selectMessageList(Map<String, Integer> paramMap) {
		
		List<Message> list = mapper.selectMessageList(paramMap.get("chattingNo"));
		if(!list.isEmpty()) {
			int result = mapper.updateReadFlag(paramMap);
		}
		
		return list;
	}
	
	//채팅 상대 검색
	@Override
	public List<Member> selectTarget(Map<String, Object> map) {
		return mapper.selectTarget(map);
	}


	//메시지 보내기
	@Override
	public int insertMessage(Message msg) {
		return mapper.insertMessage(msg);
	}

	@Override
	public int updateReadFlag(Map<String, Integer> paramMap) {
		return mapper.updateReadFlag(paramMap);
	}




	
}
