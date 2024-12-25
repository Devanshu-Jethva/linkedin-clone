package com.linkedin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.linkedin.auth.UserContextHolder;
import com.linkedin.entity.Person;
import com.linkedin.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConnectionsService {

	private final PersonRepository personRepository;

	public List<Person> getFirstDegreeConnections() {
		Long userId = UserContextHolder.getCurrentUserId();
		log.info("Getting first degree connections for user with id: {}", userId);

		return personRepository.getFirstDegreeConnections(userId);
	}

}
