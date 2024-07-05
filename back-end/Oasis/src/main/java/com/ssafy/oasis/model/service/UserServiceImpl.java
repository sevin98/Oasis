package com.ssafy.oasis.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.oasis.model.dao.UserDao;
import com.ssafy.oasis.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User login(User user) {
		return userDao.login(user);
	}

	@Override
	public int signup(User user) {
		return userDao.insertUser(user);
	}

	@Override
	public User getUser(int id) {
		return userDao.selectOne(id);
	}

	@Override
	public int modifyUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public int removeUser(int id) {
		return userDao.deleteUser(id);
	}

	@Override
	public boolean checkDuplicatedUser(String userId) {
		User user = userDao.selectOneByUserId(userId);
		if (user != null) return true;
		else return false;
	}

}
