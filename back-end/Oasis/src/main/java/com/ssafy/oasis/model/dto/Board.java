package com.ssafy.oasis.model.dto;

import java.util.Date;

public class Board {

	private int boardId;
	private int userId;
	private String title;
	private String content;
	private int viewCnt;
	private int heartCnt;
	private String location;
	private String locationDetail;
	private Date regDate;

	public Board() {
		
	}

	public Board(int boardId, int userId, String title, String content, int viewCnt, int heartCnt, String location,
			String locationDetail, Date regDate) {
		super();
		this.boardId = boardId;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.viewCnt = viewCnt;
		this.heartCnt = heartCnt;
		this.location = location;
		this.locationDetail = locationDetail;
		this.regDate = regDate;
	}

	public int getboardId() {
		return boardId;
	}

	public void setboardId(int boardId) {
		this.boardId = boardId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public int getHeartCnt() {
		return heartCnt;
	}

	public void setHeartCnt(int heartCnt) {
		this.heartCnt = heartCnt;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationDetail() {
		return locationDetail;
	}

	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", userId=" + userId + ", title=" + title + ", content=" + content
				+ ", viewCnt=" + viewCnt + ", heartCnt=" + heartCnt + ", location=" + location + ", locationDetail="
				+ locationDetail + ", regDate=" + regDate + "]";
	}

}
