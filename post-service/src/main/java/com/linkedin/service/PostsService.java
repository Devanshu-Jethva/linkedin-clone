package com.linkedin.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkedin.auth.UserContextHolder;
import com.linkedin.dto.PostCreateRequestDto;
import com.linkedin.dto.PostDto;
import com.linkedin.entity.Post;
import com.linkedin.event.PostCreatedEvent;
import com.linkedin.exception.ResourceNotFoundException;
import com.linkedin.repository.PostsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackFor = Throwable.class)
public class PostsService {

	private final PostsRepository postsRepository;
	private final ModelMapper modelMapper;
	private final KafkaTemplate<Long, PostCreatedEvent> kafkaTemplate;

	public PostDto createPost(final PostCreateRequestDto postDto) {
		Long userId = UserContextHolder.getCurrentUserId();
		Post post = modelMapper.map(postDto, Post.class);
		post.setUserId(userId);

		Post savedPost = postsRepository.save(post);

		PostCreatedEvent postCreatedEvent = PostCreatedEvent.builder().postId(savedPost.getId()).creatorId(userId)
				.content(savedPost.getContent()).build();

		kafkaTemplate.send("post-created-topic", postCreatedEvent);

		return modelMapper.map(savedPost, PostDto.class);
	}

	public PostDto getPostById(final Long postId) {
		log.debug("Retrieving post with ID: {}", postId);
		Post post = postsRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
		return modelMapper.map(post, PostDto.class);
	}

	public List<PostDto> getAllPostsOfUser(final Long userId) {
		List<Post> posts = postsRepository.findByUserId(userId);

		return posts.stream().map(element -> modelMapper.map(element, PostDto.class)).toList();
	}
}
