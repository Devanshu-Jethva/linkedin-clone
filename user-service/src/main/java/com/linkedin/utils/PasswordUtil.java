package com.linkedin.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

	// Hash a password for the first time
	public static String hashPassword(final String plainTextPassword) {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

	// Check that a plain text password matches a previously hashed one
	public static boolean checkPassword(final String plainTextPassword, final String hashedPassword) {
		return BCrypt.checkpw(plainTextPassword, hashedPassword);
	}

}
