package com.ssafy.oasis.model.dao;

import com.ssafy.oasis.model.dto.Apparel;

public interface ApparelDao {

	// 옷 조회
	public Apparel selectOne(int boardId);

	public int insertApparel(Apparel apparel);

	public int deleteApparel(int id);

	public int updateApparel(Apparel apparel);

}
