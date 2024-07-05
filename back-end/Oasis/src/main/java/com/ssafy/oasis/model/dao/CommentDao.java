package com.ssafy.oasis.model.dao;

import java.util.List;

import com.ssafy.oasis.model.dto.Comment;

public interface CommentDao {
	
	// 특정 게시글에 대한 댓글 조회
	public List<Comment> selectAllByBoardId(int boardId);
	
	// 등록
	public int insertComment(Comment comment);
	
	// 삭제
	public int deleteComment(int commenId);
	
	// 수정
	public int updateComment(Comment comment);
	
	// 댓글 개수 조회
	public int selectAllCntByBoardId(int boardId);
}
