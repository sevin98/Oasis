package com.ssafy.oasis.model.dto;

import java.util.List;

public class BoardReferenceDto {

	private Board board;
	private List<Comment> comments;
	private Apparel apparel;
	private String userId;
	private List<Image> images;

	public BoardReferenceDto(Board board, List<Comment> comments, Apparel apparel, String userId, List<Image> images) {
		super();
		this.board = board;
		this.comments = comments;
		this.apparel = apparel;
		this.userId = userId;
		this.images = images;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Apparel getApparel() {
		return apparel;
	}

	public void setApparel(Apparel apparel) {
		this.apparel = apparel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "MainReferenceDto [board=" + board + ", comments=" + comments + ", apparel=" + apparel + ", userId="
				+ userId + ", images=" + images + "]";
	}

}
