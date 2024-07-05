package com.ssafy.oasis.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.oasis.model.dao.HeartDao;
import com.ssafy.oasis.model.dao.BoardDao;
import com.ssafy.oasis.model.dto.Heart;

@Service
public class HeartServiceImpl implements HeartService {

	private final HeartDao heartDao;
	private final BoardDao boardDao;

	@Autowired
	public HeartServiceImpl(HeartDao heartDao, BoardDao boardDao) {
		this.heartDao = heartDao;
		this.boardDao = boardDao;
	}

	@Override
	public Heart getHeart(Heart heart) {
		return heartDao.getHeart(heart);
	}

	@Override
	public int addHeart(Heart heart) {
		int result = heartDao.addHeart(heart);
		if (result > 0) {
			updateHeartCount(heart.getBoardId(), true);
		}
		return result;
	}

	@Override
	public int removeHeart(Heart heart) {
		int result = heartDao.removeHeart(heart);
		if (result > 0) {
			updateHeartCount(heart.getBoardId(), false);
		}
		return result;
	}

	@Override
	public void updateHeartCount(int boardId, boolean increment) {
		if (increment) {
			boardDao.incrementHeartCount(boardId);
		} else {
			boardDao.decrementHeartCount(boardId);
		}
	}
}
