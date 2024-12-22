package com.linkedin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkedin.entity.Post;

public interface PostsRepository extends JpaRepository<Post, Long> {
	List<Post> findByUserId(Long userId);
}
