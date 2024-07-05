package com.ssafy.oasis.model.dao;

import java.util.List;

import com.ssafy.oasis.model.dto.Board;
import com.ssafy.oasis.model.dto.SearchCondition;

public interface BoardDao {

	// 게시글 검색
	public List<Board> search(SearchCondition searchCondition);

	// 전체조회
	public List<Board> selectAll();

	// 상세조회
	public Board selectOne(int id);

	// user아이디에 맞는 게시글 조회
	public List<Board> selectBoardsByUserId(int id);

	// 게시글 등록
	public int insertBoard(Board board);

	// 게시글 수정
	public int updateBoard(Board board);

	// 게시글 삭제
	public int deleteBoard(int id);

	// 조회수 증가
	public void updateViewCnt(int id);

	//하트숫자증가
	public void incrementHeartCount(int boardId);
	
	//하트숫자감소
	public void decrementHeartCount(int boardId);

}
