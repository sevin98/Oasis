package com.ssafy.oasis.model.dto;

public class SearchCondition {

	private String keyword = "none";
	private String orderBy = "none";
	private String orderByDir;
	
	public SearchCondition() {}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderByDir() {
		return orderByDir;
	}

	public void setOrderByDir(String orderByDir) {
		this.orderByDir = orderByDir;
	}

	@Override
	public String toString() {
		return "SearchCondition [keyword=" + keyword + ", orderBy=" + orderBy + ", orderByDir=" + orderByDir + "]";
	}
}
