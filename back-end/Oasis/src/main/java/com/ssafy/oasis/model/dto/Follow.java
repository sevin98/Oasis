package com.ssafy.oasis.model.dto;

public class Follow {
	private int followId;
	private int followingId;
	private int followerId;
	
	public Follow() {
	}
	
	public Follow(int followId, int followingId, int followerId) {
		super();
		this.followId = followId;
		this.followingId = followingId;
		this.followerId = followerId;
	}
	
	public int getFollowId() {
		return followId;
	}
	
	public void setFollowId(int followId) {
		this.followId = followId;
	}
	
	public int getFollowingId() {
		return followingId;
	}
	
	public void setFollowingId(int followingId) {
		this.followingId = followingId;
	}
	
	public int getFollowerId() {
		return followerId;
	}
	
	public void setFollowerId(int followerId) {
		this.followerId = followerId;
	}
	
	@Override
	public String toString() {
		return "Follow [followId=" + followId + ", followingId=" + followingId + ", followerId=" + followerId + "]";
	}
}
