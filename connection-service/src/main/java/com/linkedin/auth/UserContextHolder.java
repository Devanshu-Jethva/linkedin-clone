package com.linkedin.auth;

public class UserContextHolder {

	private UserContextHolder() {
	}

	private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();

	public static Long getCurrentUserId() {
		return currentUserId.get();
	}

	static void setCurrentUserId(final Long userId) {
		currentUserId.set(userId);
	}

	static void clear() {
		currentUserId.remove();
	}
}
