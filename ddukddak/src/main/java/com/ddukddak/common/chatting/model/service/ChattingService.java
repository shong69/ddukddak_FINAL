package com.ddukddak.common.chatting.model.service;

import java.util.List;

import com.ddukddak.common.chatting.model.dto.ChattingRoom;

public interface ChattingService {

	/** 채팅 목록 조회 및 페이지 전환
	 * @param memberNo
	 * @return
	 */
	List<ChattingRoom> selectRoomList(int memberNo);

}
