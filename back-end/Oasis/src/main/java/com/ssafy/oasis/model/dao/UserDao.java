package com.ssafy.oasis.model.dao;

import com.ssafy.oasis.model.dto.User;

public interface UserDao {
	
	// 로그인 
	public User login(User user);
	
	// 회원가입
	public int insertUser(User user);
	
	// 상세 조회
	public User selectOne(int id);
	
	// 수정
	public int updateUser(User user);
	
	// 삭제
	public int deleteUser(int id);
	
	// 아이디 중복 확인
	public User selectOneByUserId(String userId);
}
