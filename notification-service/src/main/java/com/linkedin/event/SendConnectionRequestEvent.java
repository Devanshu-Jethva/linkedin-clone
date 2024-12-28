package com.linkedin.event;

import lombok.Data;

@Data
public class SendConnectionRequestEvent {
	private Long senderId;
	private Long receiverId;
}
