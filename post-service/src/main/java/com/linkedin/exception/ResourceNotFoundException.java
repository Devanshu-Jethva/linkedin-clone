package com.linkedin.exception;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(final String message) {
		super(message);
	}
}
