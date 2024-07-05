package com.ssafy.oasis.model.dto;

public class Apparel {

	private int apparelId;
	private int boardId;
	private String outer;
	private String top;
	private String bottom;
	private String shoes;
	
	public Apparel() {
		
	}
	

	public Apparel(int apparelId, int boardId, String outer, String top, String bottom, String shoes) {
		super();
		this.apparelId = apparelId;
		this.boardId = boardId;
		this.outer = outer;
		this.top = top;
		this.bottom = bottom;
		this.shoes = shoes;
	}


	public int getapparelId() {
		return apparelId;
	}

	public void setapparelId(int apparelId) {
		this.apparelId = apparelId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getOuter() {
		return outer;
	}

	public void setOuter(String outer) {
		this.outer = outer;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getBottom() {
		return bottom;
	}

	public void setBottom(String bottom) {
		this.bottom = bottom;
	}

	public String getShoes() {
		return shoes;
	}

	public void setShoes(String shoes) {
		this.shoes = shoes;
	}

	@Override
	public String toString() {
		return "Apparel [apparelId=" + apparelId + ", boardId=" + boardId + ", outer=" + outer + ", top=" + top + ", bottom=" + bottom
				+ ", shoes=" + shoes + "]";
	}
	
}
