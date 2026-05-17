package com.moris.desktop.security.login;

import java.security.SecureRandom;

/**
 * Utility class for generating secure random passwords.
 * This class provides a method to generate passwords composed of
 * uppercase letters, lowercase letters, numbers, and special characters.
 */
public class PasswordGenerator {
	// Defines the set of uppercase and lowercase alphabetic characters used in password generation.
	private static final String       ALPHA   = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	// Defines the set of numeric characters used in password generation.
	private static final String       NUMBERS = "0123456789";
	// Defines the set of special characters used in password generation.
	// Excluded characters like single quote, double quote, backtick, and backslash to avoid potential issues in various contexts (e.g., SQL, shell commands).
	private static final String       SPECIAL = "!@#$%^&*()-_=+[]{}|;:,.<>?";
	// Combines all character sets (alphabetic, numeric, special) into a single pool for password generation.
	private static final String       POOL    = ALPHA + NUMBERS + SPECIAL;
	// SecureRandom instance used for generating cryptographically strong random numbers,
	// ensuring the randomness and unpredictability of generated passwords.
	private static final SecureRandom RANDOM  = new SecureRandom();

	/**
	 * Generates a secure random password of a specified length.
	 * The password will consist of a mix of uppercase letters, lowercase letters, numbers, and special characters.
	 *
	 * @param length The desired length of the password. Must be a positive integer.
	 * @return A randomly generated password string.
	 */
	public static String generate(int length) {
		// StringBuilder to efficiently construct the password string.
		StringBuilder sb = new StringBuilder(length);
		// Loop to append random characters from the POOL until the desired length is reached.
		for (int i = 0; i < length; i++) {
			// Selects a random character from the POOL and appends it to the StringBuilder.
			sb.append(POOL.charAt(RANDOM.nextInt(POOL.length())));
		}
		// Converts the StringBuilder content to a String and returns the generated password.
		return sb.toString();
	}
}
