package com.linkedin.exception;

public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 3888894869243738673L;

	public BadRequestException(final String message) {
		super(message);
	}
}
