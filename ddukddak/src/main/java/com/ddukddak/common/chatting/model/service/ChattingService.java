package com.ddukddak.common.chatting.model.service;

import java.util.List;
import java.util.Map;

import com.ddukddak.common.chatting.model.dto.ChattingRoom;
import com.ddukddak.common.chatting.model.dto.Message;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.partner.model.dto.Partner;

public interface ChattingService {

	/** 채팅 목록 조회 및 페이지 전환
	 * @param memberNo
	 * @return
	 */
	List<ChattingRoom> selectRoomList(int memberNo);

	/** 채팅방 번호 체크(기존에 존재하는지)
	 * @param map
	 * @return
	 */
	int checkChattingNo(Map<String, Integer> map);

	
	/** 채팅방 생성
	 * @param map
	 * @return
	 */
	int createChattingRoom(Map<String, Integer> map);
	
	
	/**채팅 입력
	 * @param msg
	 * @return
	 */
	int insertMessage(Message msg);

	/**메세지 조회
	 * @param paramMap
	 * @return
	 */
	List<Message> selectMessageList(Map<String, Integer> paramMap);

	/**메시지 읽음 처리
	 * @param paramMap
	 * @return
	 */
	int updateReadFlag(Map<String, Integer> paramMap);

	/** 채팅 상대 검색
	 * @param map
	 * @return
	 */
	List<Partner> selectTarget(Map<String, Object> map);


	

}
