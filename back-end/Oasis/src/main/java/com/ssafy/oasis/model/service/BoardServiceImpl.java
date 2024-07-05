package com.ssafy.oasis.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.oasis.model.dao.BoardDao;
import com.ssafy.oasis.model.dao.HeartDao;
import com.ssafy.oasis.model.dto.Board;
import com.ssafy.oasis.model.dto.Heart;
import com.ssafy.oasis.model.dto.SearchCondition;

@Service
public class BoardServiceImpl implements BoardService {

	private BoardDao boardDao;
	private HeartDao heartDao;

	public BoardServiceImpl(BoardDao boardDao, HeartDao heartDao) {
		this.boardDao = boardDao;
		this.heartDao = heartDao;
	}

	@Override
	public List<Board> searchBoard(SearchCondition searchcondition) {
		return boardDao.search(searchcondition);
	}
	
	@Override
	public List<Board> getBoardList(){
		return boardDao.selectAll();
	}	

	@Override
	public Board readBoard(int id) {
		boardDao.updateViewCnt(id);
		return boardDao.selectOne(id);
	}

	@Override
	public int writeBoard(Board board) {
		return boardDao.insertBoard(board);
	}

	@Override
	public int modifyBoard(Board board) {
		return boardDao.updateBoard(board);
	}

	@Override
	public int removeBoard(int id) {
		return boardDao.deleteBoard(id);
	}
	
//	@Override
//	public Heart getHeart(Heart heart) {
//		return heartDao.selectOne(heart);
//	}
//	
//	@Override
//	public int addHeart(Heart heart) {
//		heartDao.updateHeart(heart);
//		return heartDao.insertHeart(heart);
//	}
//
//	@Override
//	public int removeHeart(Heart heart) {
//		heartDao.updateHeart(heart);
//		return heartDao.deleteHeart(heart);
//	}

	@Override
	public List<Board> getBoardsByUserId(int id) {
		return boardDao.selectBoardsByUserId(id);
	}
}
