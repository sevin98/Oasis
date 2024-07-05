package com.ssafy.oasis.model.dao;

import com.ssafy.oasis.model.dto.Heart;

public interface HeartDao {
	Heart getHeart(Heart heart);

	int addHeart(Heart heart);

	int removeHeart(Heart heart);
	
	void modifyHeartCount(int boardId, boolean increment);
}
