package com.ssafy.oasis.model.service;

import com.ssafy.oasis.model.dto.User;

public interface UserService {

	// 로그인
	public User login(User user);

	// 회원가입
	public int signup(User user);

	// 상세 조회
	public User getUser(int id);

	// 수정
	public int modifyUser(User user);

	// 삭제
	public int removeUser(int id);
	
	// 아이디 중복확인
	public boolean checkDuplicatedUser(String userId);

}
