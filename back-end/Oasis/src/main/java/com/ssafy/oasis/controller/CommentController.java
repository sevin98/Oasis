package com.ssafy.oasis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.oasis.model.dto.Comment;
import com.ssafy.oasis.model.service.CommentService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/board/{boardId}/comment")
public class CommentController {
	
	private CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	// 댓글 등록
	@PostMapping
	public ResponseEntity<?> writeComment(
			@PathVariable("boardId") int boardId, 
			@RequestBody Comment comment
			) {
		
		comment.setBoardId(boardId);
//		int loginUser = (int) session.getAttribute("loginUser");
//		comment.setUserId(loginUser);
		
		int result = commentService.writeComment(comment);
		if (result == 1) return new ResponseEntity<>(HttpStatus.CREATED);
		else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	// 댓글 수정
	@PatchMapping("/{commentId}")
	public ResponseEntity<?> modifyComment(
	        @PathVariable("boardId") int boardId, 
	        @PathVariable("commentId") int commentId, 
	        @RequestBody Comment comment) {

	    comment.setBoardId(boardId);
	    comment.setCommentId(commentId);

	    // userId는 프론트에서 요청
	    int result = commentService.modifyComment(comment);
	    if (result == 1) return new ResponseEntity<>(HttpStatus.OK);
	    else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	// 댓글 삭제
	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> removeComment(@PathVariable("boardId") int boardId, @PathVariable("commentId") int commentId) {
		int result = commentService.removeComment(commentId);
		if (result == 1) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
