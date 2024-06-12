package com.ddukddak.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.board.model.dto.Board;

@Mapper
public interface tipBoardMapper {

	List<Board> selectTipList(Map<String, Object> map);

}
