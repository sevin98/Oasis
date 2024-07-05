package com.ssafy.oasis.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.oasis.model.dao.ImageDao;
import com.ssafy.oasis.model.dto.Image;

@Service
public class ImageServiceImpl implements ImageService {
	
	private ImageDao imageDao;
	
	public ImageServiceImpl(ImageDao imageDao) {
		this.imageDao = imageDao;
	}

	@Override
	public List<Image> getImages(int boardId) {
		return imageDao.selectAll(boardId);
	}

	@Override
	public Image getImage(int boardId) {
		return imageDao.selectOne(boardId);
	}

	@Override
	public int addImage(Image image) {
		return imageDao.insertImage(image);
	}

	@Override
	public int removeImage(int id) {
		return imageDao.deleteImage(id);
	}
	
	@Override
    public void removeImageByPath(String imagePath) {
        System.out.println("삭제할 이미지 경로: " + imagePath);
        imageDao.deleteByPath(imagePath);
    }

}
