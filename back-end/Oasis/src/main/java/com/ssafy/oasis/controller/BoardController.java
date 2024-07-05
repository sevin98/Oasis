package com.ssafy.oasis.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.oasis.model.dto.Apparel;
import com.ssafy.oasis.model.dto.Board;
import com.ssafy.oasis.model.dto.BoardReferenceDto;
import com.ssafy.oasis.model.dto.Comment;
import com.ssafy.oasis.model.dto.Heart;
import com.ssafy.oasis.model.dto.Image;
import com.ssafy.oasis.model.dto.SearchCondition;
import com.ssafy.oasis.model.dto.User;
import com.ssafy.oasis.model.service.ApparelService;
import com.ssafy.oasis.model.service.BoardService;
import com.ssafy.oasis.model.service.CommentService;
import com.ssafy.oasis.model.service.HeartService;
import com.ssafy.oasis.model.service.ImageService;
import com.ssafy.oasis.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/board")
public class BoardController {

	private BoardService boardService;
	private UserService userService;
	private CommentService commentService;
	private ApparelService apparelService;
	private ImageService imageService;
	private HeartService heartService;

	public BoardController(BoardService boardService, UserService userService, CommentService commentService,
			ApparelService apparelService, ImageService imageService, HeartService heartService) {
		this.boardService = boardService;
		this.userService = userService;
		this.apparelService = apparelService;
		this.commentService = commentService;
		this.imageService = imageService;
		this.heartService = heartService;
	}

	@Autowired
	private ResourceLoader loader;

	// BoardReferenceDto로
	@GetMapping
	public ResponseEntity<List<BoardReferenceDto>> list(@ModelAttribute SearchCondition searchCondition) {
		List<Board> boards = boardService.searchBoard(searchCondition);
		List<BoardReferenceDto> dtos = new ArrayList<>();

		if (boards == null || boards.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		for (Board board : boards) {
			User user = userService.getUser(board.getUserId());
			String username = user != null ? user.getUserId() : "";

			// List<Image> images = imageService.getImages(board.getboardId()); //일단 null값으로
			// 테스트, DB가없음

			// 이게 필요한가?? 수진이 일어나면 얘기해보기 => 전체 조회일 때는 필요 없음
			Apparel apparel = apparelService.getApparel(0);
			
			// list형식으로 바꾸고, dto의 매개변수도 list형식으로 바꾸고
			// 다 바꾸려면 commentService에 List로 리턴하는걸 하나 추가해야함
			List<Comment> comments = commentService.getCommentsByBoardId(board.getboardId());
			
			List<Image> images = imageService.getImages(board.getboardId()); // 일단 null값으로 테스트, DB가 없어서 그럼
			BoardReferenceDto dto = new BoardReferenceDto(board, comments, null, username, images);

			dtos.add(dto);
		}

		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	// BoardReferenceDto
	@GetMapping("/{id}")
	public ResponseEntity<BoardReferenceDto> detail(@PathVariable("id") int id, HttpSession session) {

		Board board = boardService.readBoard(id);// readboard할때 viewCnt++
		if (board == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		List<Comment> comments = commentService.getCommentsByBoardId(id) == null ? null
				: commentService.getCommentsByBoardId(id);
		Apparel apparel = apparelService.getApparel(id) == null ? null : apparelService.getApparel(id);
		String userId = userService.getUser(board.getUserId()).getUserId() == null ? null
				: userService.getUser(board.getUserId()).getUserId();
		List<Image> images = imageService.getImages(id); // 일단 null값으로 테스트, DB가 없어서 그럼

		BoardReferenceDto boardReferenceDto = new BoardReferenceDto(board, comments, apparel, userId, images);

		return new ResponseEntity<>(boardReferenceDto, HttpStatus.OK);
	}

	// 게시글 등록
	// 게시글에 이미지는 여러 개임. MultipartFile[]로 여러 개 등록할 수 있어야 함
	@PostMapping
	public ResponseEntity<?> addBoard(@RequestPart("board") Board board, @RequestPart("apparel") Apparel apparel,
			@RequestPart(value = "file", required = false) MultipartFile[] multipartFiles)
			throws IllegalStateException, IOException {

		// 게시글 저장
		int result = boardService.writeBoard(board);
		if (result == 0)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		// Apparel 저장
		apparel.setBoardId(board.getboardId());
		int apparelResult = apparelService.addApparel(apparel);
		if (apparelResult == 0)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		// 이미지 저장 로직 (생략 가능)
		if (multipartFiles != null) {
			String[] fileNames = new String[multipartFiles.length];
			int idx = 0;
			for (MultipartFile file : multipartFiles) {
				if (file.getSize() > 0) {
					String fileName = file.getOriginalFilename();
					Resource resource = loader.getResource("classpath:/static/img");
					file.transferTo(new File(resource.getFile(), fileName));
					fileNames[idx++] = fileName;
				}
			}

			for (String fileName : fileNames) {
				Image image = new Image();
				image.setBoardId(board.getboardId());
				image.setImagePath(fileName);
				imageService.addImage(image);
			}
		}
		return new ResponseEntity<>(board.getboardId(), HttpStatus.CREATED);
	}

	// 게시글 수정
	// 이미지 삭제 버튼을 누르면 그 이미지 삭제하는 url로 가서 삭제되는 거고
	// 이미지 추가된 것들은 추가되기
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateBoard(@PathVariable("id") int id, @RequestPart("board") Board board,
			@RequestPart(value = "apparel", required = false) Apparel apparel,
			@RequestPart(value = "file", required = false) MultipartFile[] multipartFiles,
			@RequestPart(value = "imagesToDelete", required = false) List<String> imagesToDelete)
			throws IllegalStateException, IOException {

		Board beforeBoard = boardService.readBoard(id);
		if (beforeBoard == null)
			return new ResponseEntity<>("not found Board", HttpStatus.BAD_REQUEST);

		board.setboardId(id);
		if (board.getTitle() == null)
			board.setTitle(beforeBoard.getTitle());
		if (board.getContent() == null)
			board.setContent(beforeBoard.getContent());
		if (board.getLocation() == null)
			board.setLocation(beforeBoard.getLocation());
		if (board.getLocationDetail() == null)
			board.setLocationDetail(beforeBoard.getLocationDetail());

		if (apparel != null) {
			apparel.setBoardId(id);
			apparelService.modifyApparel(apparel);
		}

		int result = boardService.modifyBoard(board);
		if (result == 0) {
			return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
		}

		List<Comment> comments = commentService.getCommentsByBoardId(id) == null ? null
				: commentService.getCommentsByBoardId(id);
		Apparel updatedApparel = apparelService.getApparel(id);
		String userId = userService.getUser(board.getUserId()).getUserId() == null ? null
				: userService.getUser(board.getUserId()).getUserId();

		// 삭제할 이미지 처리
		if (imagesToDelete != null && !imagesToDelete.isEmpty()) {
			for (String imagePath : imagesToDelete) {
				imageService.removeImageByPath(imagePath);
				Resource resource = loader.getResource("classpath:/static/img");
				File file = new File(resource.getFile(), imagePath);
				if (file.exists()) {
					file.delete();
				}
			}
		}

		// 이미지 등록
		if (multipartFiles != null) {
			String[] fileNames = new String[multipartFiles.length];

			int idx = 0;
			for (MultipartFile file : multipartFiles) {
				if (file.getSize() > 0) {
					String fileName = file.getOriginalFilename();

					Resource resource = loader.getResource("classpath:/static/img");
					file.transferTo(new File(resource.getFile(), fileName));
					fileNames[idx++] = fileName;
				}
			}

			for (String fileName : fileNames) {
				Image image = new Image();
				image.setBoardId(id);
				image.setImagePath(fileName);
				imageService.addImage(image);
			}
		}

		List<Image> images = imageService.getImages(id);
		BoardReferenceDto boardReferenceDto = new BoardReferenceDto(board, comments, updatedApparel, userId, images);
		return new ResponseEntity<>(boardReferenceDto, HttpStatus.OK);
	}

	// 게시글 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
		int result = boardService.removeBoard(id);
		if (result == 1) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// 좋아요 토글
	@PostMapping("/{id}/heart")
	public ResponseEntity<Integer> toggleHeart(@PathVariable("id") int id, @RequestBody Heart heart) {
		heart.setBoardId(id);

		boolean isHeart = heartService.getHeart(heart) != null;
		int result;

		if (isHeart) {
			System.out.println("좋아요 삭제 시도");
			result = heartService.removeHeart(heart);
			if (result > 0) {
				System.out.println("좋아요 삭제 성공");
				return new ResponseEntity<>(1, HttpStatus.OK);
			} else {
				System.out.println("좋아요 삭제 실패");
				return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
			}
		} else {
			System.out.println("좋아요 등록 시도");
			result = heartService.addHeart(heart);
			if (result > 0) {
				System.out.println("좋아요 등록 성공");
				return new ResponseEntity<>(1, HttpStatus.OK);
			} else {
				System.out.println("좋아요 등록 실패");
				return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	@GetMapping("{id}/heart/{userId}")
	public ResponseEntity<?> getHeart(@PathVariable int id, @PathVariable int userId) {
		Heart heart = new Heart();
		heart.setUserId(userId);
		heart.setBoardId(id);
		boolean isHeart = heartService.getHeart(heart) != null;
		if (isHeart) {
			return new ResponseEntity<>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
	}

}
