package com.ssafy.oasis.model.service;

import java.util.List;

import com.ssafy.oasis.model.dto.MainReferenceDto;

public interface MainService {
	// 지도 api에 넘길 데이터 조회
	public List<MainReferenceDto> getMapDatas();

	// 인기 많은 장소 top3 조회
	public List<MainReferenceDto> getTop3PopularDatas();
}
