package com.ddukddak.common.chatting.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.common.chatting.model.dto.ChattingRoom;
import com.ddukddak.common.chatting.model.dto.Message;
import com.ddukddak.member.model.dto.Member;

@Mapper
public interface ChattingMapper {

	/**채팅방 목록 조회
	 * @param memberNo
	 * @return
	 */
	List<ChattingRoom> selectRoomList(int memberNo);

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
	 * @param integer
	 * @return
	 */
	List<Message> selectMessageList(Integer integer);

	/** 읽음 상태 변경
	 * @param paramMap
	 * @return
	 */
	int updateReadFlag(Map<String, Integer> paramMap);

	
	/** 채팅 상대 검색
	 * @param map
	 * @return
	 */
	List<Member> selectTarget(Map<String, Object> map);

	/** 메시지 보내기
	 * @param msg
	 * @return
	 */
	int insertMessage(Message msg);


}
