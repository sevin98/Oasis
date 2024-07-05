package com.ssafy.oasis.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.oasis.model.dao.CommentDao;
import com.ssafy.oasis.model.dto.Comment;

@Service
public class CommentServiceImpl implements CommentService {
	
	private CommentDao commentDao;
	
	public CommentServiceImpl(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	@Override
	public List<Comment> getCommentsByBoardId(int boardId) {
		return commentDao.selectAllByBoardId(boardId);
	}

	@Override
	public int writeComment(Comment comment) {
		return commentDao.insertComment(comment);
	}

	@Override
	public int removeComment(int id) {
		return commentDao.deleteComment(id);
	}

	@Override
	public int modifyComment(Comment comment) {
		return commentDao.updateComment(comment);
	}

	@Override
	public int getCommentCntByBoardId(int boardId) {
		return commentDao.selectAllCntByBoardId(boardId);
	}

}
