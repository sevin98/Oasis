package com.ssafy.oasis.model.service;

import java.util.List;

import com.ssafy.oasis.model.dto.Comment;

public interface CommentService {

	// 특정 게시글에 대한 댓글 조회
	public List<Comment> getCommentsByBoardId(int boardId);

	// 등록
	public int writeComment(Comment comment);

	// 삭제
	public int removeComment(int id);

	// 수정
	public int modifyComment(Comment comment);

	// 댓글 개수 조회
	public int getCommentCntByBoardId(int boardId);
}
