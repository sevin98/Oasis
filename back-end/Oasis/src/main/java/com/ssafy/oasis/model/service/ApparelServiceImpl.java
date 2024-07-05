package com.ssafy.oasis.model.service;

import org.springframework.stereotype.Service;


import com.ssafy.oasis.model.dao.ApparelDao;
import com.ssafy.oasis.model.dto.Apparel;
@Service
public class ApparelServiceImpl implements ApparelService {
	
	private ApparelDao apparelDao;
	
	public ApparelServiceImpl(ApparelDao apparelDao) {
		this.apparelDao = apparelDao;
	}

	@Override
	public Apparel getApparel(int boardId) {
		return apparelDao.selectOne(boardId);
	}

	@Override
	public int addApparel(Apparel apparel) {
		return apparelDao.insertApparel(apparel);
	}

	@Override
	public int removeApparel(int id) {
		return apparelDao.deleteApparel(id);
	}

	@Override
	public int modifyApparel(Apparel apparel) {
		return apparelDao.updateApparel(apparel);
	}

}
