package com.ssafy.oasis.model.service;

import java.util.List;

import com.ssafy.oasis.model.dto.Board;
import com.ssafy.oasis.model.dto.Heart;
import com.ssafy.oasis.model.dto.SearchCondition;

public interface BoardService {

	// 검색
	public List<Board> searchBoard(SearchCondition searchcondition);

	// 전체조회
	public List<Board> getBoardList();
	
	// 상세조회
	public Board readBoard(int id);

	// 게시글 등록
	public int writeBoard(Board board);

	// 게시글 수정
	public int modifyBoard(Board board);

	// 게시글 삭제
	public int removeBoard(int id);

//	// 좋아요 가져오기
//	public Heart getHeart(Heart heart);
//
//	// 좋아요 기능
//	public int addHeart(Heart heart);
//
//	// 좋아요 해제
//	public int removeHeart(Heart heart);
	
	// user아이디에 맞는 게시글 조회
	public List<Board> getBoardsByUserId(int id);
}
