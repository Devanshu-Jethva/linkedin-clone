package com.linkedin.event;

import lombok.Data;

@Data
public class AcceptConnectionRequestEvent {
	private Long senderId;
	private Long receiverId;
}