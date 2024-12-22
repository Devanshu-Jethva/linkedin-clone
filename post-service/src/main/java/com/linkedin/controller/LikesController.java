package com.linkedin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkedin.service.PostLikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesController {

	private final PostLikeService postLikeService;

	@PostMapping("/{postId}")
	public ResponseEntity<Void> likePost(@PathVariable final Long postId) {
		postLikeService.likePost(postId, 1L);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> unlikePost(@PathVariable final Long postId) {
		postLikeService.unlikePost(postId, 1L);
		return ResponseEntity.noContent().build();
	}

}
