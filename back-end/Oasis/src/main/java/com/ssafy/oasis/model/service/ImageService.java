package com.ssafy.oasis.model.service;

import java.util.List;

import com.ssafy.oasis.model.dto.Image;

public interface ImageService {

	// 전체 이미지 조회(게시글의)
	public List<Image> getImages(int boardId);

	// 이미지 조회(게시글의)
	public Image getImage(int boardId);

	// 이미지 삽입
	public int addImage(Image image);

	// 이미지 삭제
	public int removeImage(int id);

	// 이미지패스기반 이미지 삭제
	void removeImageByPath(String imagePath);
}
