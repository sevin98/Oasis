package com.ssafy.oasis.model.dao;

import java.util.List;

import com.ssafy.oasis.model.dto.MainReferenceDto;

public interface MainDao {
	
	// 지도 api에 넘길 데이터 조회
	public List<MainReferenceDto> selectMapDatas();
	
	// 인기 많은 장소 top3 조회
	public List<MainReferenceDto> selectTop3PopularDatas();
}
