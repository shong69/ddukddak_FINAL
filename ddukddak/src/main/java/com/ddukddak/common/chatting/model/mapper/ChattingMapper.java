package com.ddukddak.common.chatting.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.common.chatting.model.dto.ChattingRoom;
import com.ddukddak.common.chatting.model.dto.Message;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.partner.model.dto.Partner;

@Mapper
public interface ChattingMapper {

	/**채팅방 목록 조회
	 * @param memberNo
	 * @return
	 */
	List<ChattingRoom> selectRoomList(Map<String, Object> map);

	/** 채팅방 생성 여부 체크
	 * @param map
	 * @return
	 */
	int checkChattingNo(Map<String, Integer> map);

	/** 새로운 채팅방 생성
	 * @param map
	 * @return
	 */
	int createChattingRoom(Map<String, Integer> map);

	/** 채팅 기록 불러오기
	 * @param paramMap
	 * @return
	 */
	List<Message> selectMessageList(Map<String, Object> paramMap);

	/** 읽음 상태 변경
	 * @param paramMap
	 * @return
	 */
	int updateReadFlag(Map<String, Object> paramMap);

	
	/** 채팅 상대 검색
	 * @param map
	 * @return
	 */
	List<Partner> selectTarget(Map<String, Object> map);

	/** 메시지 보내기
	 * @param msg
	 * @return
	 */
	int insertMessage(Message msg);

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

	/**[시공사]읽음 표시 업데이트
	 * @param paramMap
	 * @return
	 */
	int IupdateReadFlag(Map<String, Object> paramMap);

	/**[챗봇] 주문정보 리턴
	 * @param orderNo
	 * @return
	 */
	String orderInfo(String orderNo);




}
