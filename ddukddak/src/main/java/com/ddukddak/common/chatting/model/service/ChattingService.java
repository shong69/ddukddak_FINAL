package com.ddukddak.common.chatting.model.service;

import java.util.List;
import java.util.Map;

import com.ddukddak.common.chatting.model.dto.ChattingRoom;
import com.ddukddak.common.chatting.model.dto.Message;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.myPage.model.dto.Order;
import com.ddukddak.partner.model.dto.Partner;

public interface ChattingService {
	/**채팅 목록 조회
	 * @param memberNo
	 * @param map
	 * @return
	 */
	List<ChattingRoom> selectRoomList(Map<String, Object> map);

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
	List<Message> selectMessageList(Map<String, Object> paramMap);

	/**메시지 읽음 처리
	 * @param paramMap
	 * @return
	 */
	int updateReadFlag(Map<String, Object> paramMap);

	/** 채팅 상대 검색
	 * @param map
	 * @return
	 */
	List<Partner> selectTarget(Map<String, Object> map);

	/**[시공사]채팅 상대 검색
	 * @param map
	 * @return
	 */
	List<Member> IselectTarget(Map<String, Object> map);

	/**[시공사]채팅방 검색
	 * @param map
	 * @return
	 */
	int IcheckChattingNo(Map<String, Integer> map);
	/**[시공사]채팅방 생성
	 * @param map
	 * @return
	 */
	int IcreateChattingRoom(Map<String, Integer> map);

	/**[시공사]메시지 리스트 조회
	 * @param paramMap
	 * @return
	 */
	List<Message> IselectMessageList(Map<String, Object> paramMap);

	/**[시공사]채팅 읽음 표시
	 * @param paramMap
	 * @return
	 */
	int IupdateReadFlag(Map<String, Object> paramMap);

	/**[챗봇]
	 * @param map
	 * @return
	 */
	List<Order> orderInfo(Map<String, Object> map);



	

	


	

}
