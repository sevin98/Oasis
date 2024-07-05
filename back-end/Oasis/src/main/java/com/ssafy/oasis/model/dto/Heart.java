package com.ssafy.oasis.model.dto;

public class Heart {
	private int boardId;
	private int userId;

	public Heart() {
	}

	public Heart(int boardId, int userId) {
		this.boardId = boardId;
		this.userId = userId;
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
}
