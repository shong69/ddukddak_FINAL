package com.ddukddak.common.chatting.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.common.chatting.model.dto.ChattingRoom;

@Mapper
public interface ChattingMapper {

	/**채팅방 목록 조회
	 * @param memberNo
	 * @return
	 */
	List<ChattingRoom> selectRoomList(int memberNo);

}
