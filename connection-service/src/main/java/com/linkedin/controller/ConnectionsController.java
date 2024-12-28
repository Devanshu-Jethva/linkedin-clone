package com.linkedin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkedin.entity.Person;
import com.linkedin.service.ConnectionsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/connections")
@RequiredArgsConstructor
public class ConnectionsController {

	private final ConnectionsService connectionsService;

	@GetMapping("/first-degree")
	public ResponseEntity<List<Person>> getFirstConnections() {
		return ResponseEntity.ok(connectionsService.getFirstDegreeConnections());
	}

	@PostMapping("/request/{userId}")
	public ResponseEntity<Boolean> sendConnectionRequest(@PathVariable final Long userId) {
		return ResponseEntity.ok(connectionsService.sendConnectionRequest(userId));
	}

	@PostMapping("/accept/{userId}")
	public ResponseEntity<Boolean> acceptConnectionRequest(@PathVariable final Long userId) {
		return ResponseEntity.ok(connectionsService.acceptConnectionRequest(userId));
	}

	@PostMapping("/reject/{userId}")
	public ResponseEntity<Boolean> rejectConnectionRequest(@PathVariable final Long userId) {
		return ResponseEntity.ok(connectionsService.rejectConnectionRequest(userId));
	}
}
