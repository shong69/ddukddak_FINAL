package com.ddukddak.board.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.board.model.dto.BoardImg;
import com.ddukddak.board.model.exception.BoardInsertException;
import com.ddukddak.board.model.mapper.MyHouseBoardMapper;
import com.ddukddak.common.util.Utility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor=Exception.class)
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
@Slf4j
public class MyHouseServiceImpl implements MyHouseBoardService {
	
	private final MyHouseBoardMapper mapper;
	
	@Value("${my.myHouse.web-path}")
	private String webPath;
	
	@Value("${my.myHouse.folder-path}")
	private String folderPath;
	
	// 집들이 게시글 작성
	@Override
	public int insertBoard(Board board, List<MultipartFile> imgList) throws IOException{
		
		int result = mapper.boardInsert(board);
		
		if(result == 0) return 0;
		
		
		int boardNo = board.getBoardNo();
		
		log.info("boardNo : " + boardNo);
		
		List<BoardImg> uploadList = new ArrayList<>();
		
		if(!imgList.isEmpty()) {
			
			for(int i = 0; i < imgList.size(); i++) {
				
				// 원본명
				String originalName = imgList.get(i).getOriginalFilename();
				
				// 변경명
				String rename = Utility.fileRename(originalName);
				
				BoardImg img = BoardImg.builder()
							   .uploadImgOgName(originalName)
							   .uploadImgRename(rename)
							   .uploadImgPath(webPath)
							   .boardNo(boardNo)
							   .uploadImgOrder(i)
							   .uploadFile(imgList.get(i))
							   .build();
				
				uploadList.add(img);
				
			}
			
		}
		
		if(uploadList.isEmpty()) {
			return boardNo;
		}
		
		result = mapper.insertUploadList(uploadList);
		
		if(result == uploadList.size()) {
			
			for(BoardImg img : uploadList) {
				
				img.getUploadFile().transferTo( new File(folderPath + img.getUploadImgRename()) );
				
			}
			
		} else {
			
			throw new BoardInsertException("이미지가 정상 삽입되지 않음");
			
		}
		return boardNo;
	}

	
	// 집들이 게시판 리스트 조회
	@Override
	public List<Board> selectMyHouseList(int boardType) {
		
		return mapper.selectMyHouseList(boardType);
	}

}























