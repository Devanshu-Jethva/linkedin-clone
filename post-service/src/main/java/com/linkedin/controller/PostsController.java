package com.linkedin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkedin.dto.PostCreateRequestDto;
import com.linkedin.dto.PostDto;
import com.linkedin.service.PostsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {

	private final PostsService postsService;

	@PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody final PostCreateRequestDto postDto) {
		PostDto createdPost = postsService.createPost(postDto);
		return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable final Long postId) {
		PostDto postDto = postsService.getPostById(postId);
		return ResponseEntity.ok(postDto);
	}

	@GetMapping("/users/{userId}/allPosts")
	public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable final Long userId) {
		List<PostDto> posts = postsService.getAllPostsOfUser(userId);
		return ResponseEntity.ok(posts);
	}

}
