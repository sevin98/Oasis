package com.ssafy.oasis.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.oasis.model.dao.MainDao;
import com.ssafy.oasis.model.dto.MainReferenceDto;

@Service
public class MainServiceImpl implements MainService {
	
	private MainDao mainDao;
	
	public MainServiceImpl(MainDao mainDao) {
		this.mainDao = mainDao;
	}

	@Override
	public List<MainReferenceDto> getMapDatas() {
		return mainDao.selectMapDatas();
	}

	@Override
	public List<MainReferenceDto> getTop3PopularDatas() {
		return mainDao.selectTop3PopularDatas();
	}

}
