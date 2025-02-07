package com.linkedin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkedin.entity.PostLike;
import com.linkedin.exception.BadRequestException;
import com.linkedin.exception.ResourceNotFoundException;
import com.linkedin.repository.PostLikeRepository;
import com.linkedin.repository.PostsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackFor = Throwable.class)
public class PostLikeService {

	private final PostLikeRepository postLikeRepository;
	private final PostsRepository postsRepository;

	public void likePost(final Long postId, final Long userId) {
		log.info("Attempting to like the post with id: {}", postId);
		boolean exists = postsRepository.existsById(postId);
		if (!exists) {
			throw new ResourceNotFoundException("Post not found with id: " + postId);
		}

		boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
		if (alreadyLiked) {
			throw new BadRequestException("Cannot like the same post again.");
		}

		PostLike postLike = new PostLike();
		postLike.setPostId(postId);
		postLike.setUserId(userId);
		postLikeRepository.save(postLike);
		log.info("Post with id: {} liked successfully", postId);
	}

	public void unlikePost(final Long postId, final Long userId) {
		log.info("Attempting to unlike the post with id: {}", postId);
		boolean exists = postsRepository.existsById(postId);
		if (!exists) {
			throw new ResourceNotFoundException("Post not found with id: " + postId);
		}

		boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
		if (!alreadyLiked) {
			throw new BadRequestException("Cannot unlike the post which is not liked.");
		}

		postLikeRepository.deleteByUserIdAndPostId(userId, postId);

		log.info("Post with id: {} unliked successfully", postId);
	}
}
