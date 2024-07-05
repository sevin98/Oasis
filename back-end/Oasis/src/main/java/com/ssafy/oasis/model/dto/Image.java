package com.ssafy.oasis.model.dto;

public class Image {

	private int imageId;
	private int boardId;
	private String imagePath;
	
	public Image() {
		
	}
	
	public Image(int imageId, int boardId, String imagePath) {
		this.imageId = imageId;
		this.boardId = boardId;
		this.imagePath = imagePath;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}


	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "Image [imageId=" + imageId + ", boardId=" + boardId + ", imagePath=" + imagePath + "]";
	}


	
}
