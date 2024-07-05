package com.ssafy.oasis.model.dto;

public class Comment {
	private int commentId;
	private int boardId;
	private int userId;
	private String loginUser;
	private String content;
	
	public Comment() {
	}

	public Comment(int commentId, int boardId, int userId, String loginUser, String content) {
		super();
		this.commentId = commentId;
		this.boardId = boardId;
		this.userId = userId;
		this.loginUser = loginUser;
		this.content = content;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", boardId=" + boardId + ", userId=" + userId + ", loginUser="
				+ loginUser + ", content=" + content + "]";
	}

}
