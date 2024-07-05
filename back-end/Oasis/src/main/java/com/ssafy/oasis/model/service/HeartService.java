package com.ssafy.oasis.model.service;

import com.ssafy.oasis.model.dto.Heart;

public interface HeartService {
	Heart getHeart(Heart heart);

	int addHeart(Heart heart);

	int removeHeart(Heart heart);

	void updateHeartCount(int boardId, boolean increment);
}
