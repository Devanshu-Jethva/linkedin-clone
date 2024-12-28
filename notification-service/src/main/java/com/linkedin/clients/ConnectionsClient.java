package com.linkedin.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.linkedin.dto.PersonDto;

@FeignClient(name = "connection-service", path = "/connection-service")
public interface ConnectionsClient {

	@GetMapping("/connections/first-degree")
	List<PersonDto> getFirstConnections(@RequestHeader("X-User-Id") Long userId);

}
