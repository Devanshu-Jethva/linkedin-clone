package com.linkedin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkedin.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);
}
