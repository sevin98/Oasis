package com.ssafy.oasis.model.dao;

import java.util.List;

import com.ssafy.oasis.model.dto.Image;

public interface ImageDao {
	
	//전체 이미지 조회(게시글의)
	public List<Image> selectAll(int boardId);
	
	//이미지 조회(게시글의)
	public Image selectOne(int boardId);
	
	//이미지 삽입
	public int insertImage(Image image);
	
	//이미지 수정
//	public int updateImage(Image image);

	//이미지 삭제
	public int deleteImage(int id);
	
	void deleteByPath(String imagePath);
}
