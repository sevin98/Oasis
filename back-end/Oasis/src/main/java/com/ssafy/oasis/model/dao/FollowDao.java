package com.ssafy.oasis.model.dao;

import java.util.List;

import com.ssafy.oasis.model.dto.Follow;

public interface FollowDao {
	
	// 팔로잉 조회
	// 반환값 : followingId (id == followerId)
	public List<Integer> selectFollowings(int id);
	
	// 팔로잉
	// 팔로우 하는 사람 : followerId
	// 팔로우 당하는 사람 : followingId
	public int insertFollow(Follow follow);
	
	// 팔로워 조회
	// 반환값 : followerId (id == followingId)
	public List<Integer> selectFollowers(int id);
	
	// 팔로잉 끊기
	// 끊는 사람 : followerId
	// 끊음을 당하는 삶 : followingId
	public int deleteFollow(Follow follow);

}
