
package com.ssafy.oasis.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.oasis.model.dto.Board;
import com.ssafy.oasis.model.dto.Image;
import com.ssafy.oasis.model.dto.User;
import com.ssafy.oasis.model.service.BoardService;
import com.ssafy.oasis.model.service.FollowService;
import com.ssafy.oasis.model.service.ImageService;
import com.ssafy.oasis.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

	private ResourceLoader loader;
	private UserService userService;
	private BoardService boardService;
	private ImageService imageService;
	private FollowService followService;

	public UserController(ResourceLoader loader, UserService userService, BoardService boardService, ImageService imageService, FollowService followService) {
		this.loader = loader;
		this.userService = userService;
		this.boardService = boardService;
		this.imageService = imageService;
		this.followService = followService;
	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {
		User loginUser = userService.login(user);
		if (loginUser != null) {
			session.setAttribute("id", loginUser.getId());
			session.setAttribute("loginUser", loginUser.getUserId());
			return new ResponseEntity<>(loginUser.getId(), HttpStatus.OK);
		} else {
			// 로그인 실패 시 400 반환
			return new ResponseEntity<>("fail login", HttpStatus.BAD_REQUEST);
		}
	}

	// 로그아웃
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<>("success logout", HttpStatus.OK);
	}

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user) {
		if (userService.checkDuplicatedUser(user.getUserId())) {
			return new ResponseEntity<>("duplicate exception", HttpStatus.BAD_REQUEST);
		}

		int result = userService.signup(user);
		if (result == 1)
			return new ResponseEntity<>(HttpStatus.CREATED);
		else
			return new ResponseEntity<>("fail logout", HttpStatus.BAD_REQUEST);
	}

	// 회원 정보 조회
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") int id) {
		User user = userService.getUser(id);
		if (user == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		List<Image> images = new ArrayList<>();
		List<Board> boards = boardService.getBoardsByUserId(user.getId());
		for (Board board : boards) {
			images.add(imageService.getImage(board.getboardId()));
		}
		
		List<Integer> followingsId = followService.getFollowings(id);
		List<User> followings = new ArrayList<>();
		for (int following : followingsId) {
			followings.add(userService.getUser(following));
		}
		List<Integer> followersId = followService.getFollowers(id);
		List<User> followers = new ArrayList<>();
		for (int follower : followersId) {
			followers.add(userService.getUser(follower));
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("images", images);
		map.put("followings", followings);
		map.put("followers", followers);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	// 회원 정보 수정
	@PatchMapping("/{id}")
	public ResponseEntity<?> modifyUser(@PathVariable("id") int id, @RequestPart(name="file", required = false) MultipartFile multipartFile, @RequestPart("user") User user) throws IllegalStateException, IOException {
		String fileName = "";
		if (multipartFile != null && multipartFile.getSize() > 0) {
			fileName = multipartFile.getOriginalFilename();
			
			Resource resource = loader.getResource("classpath:/static/img");
			multipartFile.transferTo(new File(resource.getFile(), fileName));
			System.out.println(resource.getFile() + " " + fileName);
		}
		
		User beforeUser = userService.getUser(id); 
		if (beforeUser == null) return new ResponseEntity<>("not found user", HttpStatus.BAD_REQUEST);

		user.setId(id);
		if (user.getUserId() == null) user.setUserId(beforeUser.getUserId());
		if (user.getEmail() == null) user.setEmail(beforeUser.getEmail());
		if (user.getAge() == 0) user.setAge(beforeUser.getAge());
		if (user.getGender() == null) user.setGender(beforeUser.getGender());
		if (fileName.equals("")) user.setProfileImage(beforeUser.getProfileImage());
		else user.setProfileImage(fileName);
		
		int result = userService.modifyUser(user);
		if (result == 1)
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	// 회원 탈퇴
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeUser(@PathVariable("id") int id) {
		int result = userService.removeUser(id);
		if (result == 1)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
