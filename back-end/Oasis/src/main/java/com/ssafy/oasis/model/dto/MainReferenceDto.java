package com.ssafy.oasis.model.dto;

public class MainReferenceDto {
	private int boardId;
	private String title;
	private String locationDetail;
	private String imagePath;
	
	public MainReferenceDto() {
	}

	public MainReferenceDto(int boardId, String title, String locationDetail, String imagePath) {
		super();
		this.boardId = boardId;
		this.title = title;
		this.locationDetail = locationDetail;
		this.imagePath = imagePath;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocationDetail() {
		return locationDetail;
	}

	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "MainReferenceDto [boardId=" + boardId + ", title=" + title + ", locationDetail=" + locationDetail
				+ ", imagePath=" + imagePath + "]";
	}
}