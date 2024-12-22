package com.linkedin.exception;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -3419434485856742656L;

	public ResourceNotFoundException(final String message) {
		super(message);
	}
}
