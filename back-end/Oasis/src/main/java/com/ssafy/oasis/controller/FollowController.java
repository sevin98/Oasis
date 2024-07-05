package com.ssafy.oasis.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.oasis.model.dto.Follow;
import com.ssafy.oasis.model.dto.User;
import com.ssafy.oasis.model.service.FollowService;
import com.ssafy.oasis.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user/{id}")
public class FollowController {

	private UserService userService;
	private FollowService followService;

	public FollowController(UserService userService, FollowService followService) {
		this.userService = userService;
		this.followService = followService;
	}

	// 팔로잉 조회
	@GetMapping("/following")
	public ResponseEntity<?> getFollowings(@PathVariable("id") int id) {
		List<Integer> followings = followService.getFollowings(id);
		List<User> users = new ArrayList<>();
		for (int following : followings) {
			users.add(userService.getUser(following));
		}

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	// 팔로잉
	// 팔로우 하는 사람 : followerId
	// 팔로우 당하는 사람 : followingId
	@PostMapping("/following")
	public ResponseEntity<?> following(@PathVariable("id") int followId, @RequestBody Follow follow, HttpSession session) {
//		int loginUser = (int) session.getAttribute("id");
		

		int result = followService.addFollow(follow);
		if (result == 1)
			return new ResponseEntity<>(HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	// 팔로워 조회
	@GetMapping("/follower")
	public ResponseEntity<?> getFollowers(@PathVariable("id") int id) {
		List<Integer> followers = followService.getFollowers(id);
		List<User> users = new ArrayList<>();
		for (int follower : followers) {
			users.add(userService.getUser(follower));
		}

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	// 팔로잉 끊기
	// 끊는 사람 : followerId
	// 끊음을 당하는 삶 : followingId
	@DeleteMapping("/following")
	public ResponseEntity<?> removeFollow(@PathVariable("id") int followId, @RequestBody Follow follow, HttpSession session) {
//		int loginUser = (int) session.getAttribute("loginUser");

		
		int result = followService.removeFollow(follow);
		if (result == 1)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>("fail remove follow", HttpStatus.BAD_REQUEST);
	}

}
