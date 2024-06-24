package com.ddukddak.board.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
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
import com.ddukddak.main.model.dto.Pagination;

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

	
//	// 집들이 게시판 리스트 조회
//	@Override
//	public List<Board> selectMyHouseList(int boardType) {
//		
//		return mapper.selectMyHouseList(boardType);
//	}

	@Override
	public Map<String, Object> selectMyHouseList(int boardCode, String sort, int cp) {
		
		int listCount = mapper.getListCount(boardCode);
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset,limit);
		
		Map<String, Object> newMap = new HashMap<>();
		
		newMap.put("boardCode", boardCode);		
		newMap.put("sort", sort);
		
		List<Board> myHouseList = mapper.selectMyHouseList(newMap, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("myHouseList", myHouseList);
		
		return map;
	}


	// 검색 서비스
	@Override
	public Map<String, Object> searchList(int boardCode, String sort, String query, int cp) {
		
		Map<String, Object> countMap = new HashMap<>();
		
		countMap.put("boardCode", boardCode);
		countMap.put("query", query);
		
		int listCount = mapper.getSearchCount(countMap);
		
		log.info("listCount : " + listCount);
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		Map<String, Object> newMap = new HashMap<>();
		
		newMap.put("boardCode", boardCode);
		newMap.put("sort", sort);
		newMap.put("query", query);
		
		List<Board> myHouseList = mapper.selectSearchList(newMap, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("myHouseList", myHouseList);
		
		return map;
	}


	// 게시글 상세 조회
	@Override
	public Board selectBoard(Map<String, Object> map) {
		
		return mapper.selectBoard(map);
	}


	// 조회수 증가
	@Override
	public int updateReadCount(int boardNo) {
		
		// 조회수 증가
		int result = mapper.updateReadCount(boardNo);
		
		// 현재 조회수 카운트 조회 (증가한 현재 조회수 다시 조회)
		if(result > 0) {
			return mapper.selectReadCount(boardNo);
		}
		
		return -1;
	}


	// 이미지 리스트 조회
	@Override
	public List<BoardImg> selectImageList(int boardNo) {
		
		return mapper.selectImageList(boardNo);
	}


	// 메인페이지용 집들이 게시물 조회
	@Override
	public List<Board> selectMyHouseList() {
		return mapper.selectMyHouseList2();
	}


	// 집들이 게시글 삭제
	@Override
	public int deleteMyHouse(int boardNo) {
		
		return mapper.deleteMyHouse(boardNo);
	}


	// 집들이 게시글 수정
//	@Override
//	public int updateMyHouse(Board board, List<MultipartFile> images) throws IllegalStateException, IOException {
//		
//		int result = mapper.updateMyHouse(board);
//		
//		if(result == 0) return 0;
//		
//		List<BoardImg> uploadList = new ArrayList<>();
//		
//		for(int i = 0; i < images.size(); i ++) {
//			
//			if( !images.get(i).isEmpty() ) {
//				String originalName =images.get(i).getOriginalFilename();
//				String rename = Utility.fileRename(originalName);
//				
//				BoardImg img = BoardImg.builder()
//							   .uploadImgOgName(originalName)
//							   .uploadImgRename(rename)
//							   .uploadImgPath(webPath)
//							   .boardNo(board.getBoardNo())
//							   .uploadImgOrder(i)
//							   .uploadFile(images.get(i))
//							   .build();
//				
//				uploadList.add(img);
//				
//				result = mapper.updateImage(img);
//			}
//			
//			if(uploadList.isEmpty()) {
//				return result;
//			}
//			
//			for(BoardImg img : uploadList) {
//				img.getUploadFile().transferTo(new File(folderPath + img.getUploadImgRename()));
//			}
//			
//		}
//		
//		return result;
//	}


	@Override
	public int updateBoard(Board board) {
		return mapper.updateBoard(board);
	}


	@Override
	public void updateBoardImages(int boardNo, List<BoardImg> images) {
		// Delete existing images for the board
        mapper.deleteImagesByBoardNo(boardNo);

        // Insert new images
        for (BoardImg image : images) {
            mapper.insertImage(image);
        }
	}




}























