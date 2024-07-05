package com.ssafy.oasis.model.service;

import com.ssafy.oasis.model.dto.Apparel;

public interface ApparelService {

	public Apparel getApparel(int boardId);

	public int addApparel(Apparel apparel);

	public int removeApparel(int id);

	public int modifyApparel(Apparel apparel);
	
}
