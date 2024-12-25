package com.linkedin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/first/degree")
	public ResponseEntity<List<Person>> getFirstConnections() {
		return ResponseEntity.ok(connectionsService.getFirstDegreeConnections());
	}
}
