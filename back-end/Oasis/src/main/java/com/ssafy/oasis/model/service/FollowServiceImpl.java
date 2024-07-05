package com.ssafy.oasis.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.oasis.model.dao.FollowDao;
import com.ssafy.oasis.model.dto.Follow;

@Service
public class FollowServiceImpl implements FollowService {
	
	private FollowDao followDao;
	
	public FollowServiceImpl(FollowDao followDao) {
		this.followDao = followDao;
	}

	@Override
	public List<Integer> getFollowings(int id) {
		return followDao.selectFollowings(id);
	}

	@Override
	public int addFollow(Follow follow) {
		return followDao.insertFollow(follow);
	}

	@Override
	public List<Integer> getFollowers(int id) {
		return followDao.selectFollowers(id);
	}

	@Override
	public int removeFollow(Follow follow) {
		return followDao.deleteFollow(follow);
	}

}
