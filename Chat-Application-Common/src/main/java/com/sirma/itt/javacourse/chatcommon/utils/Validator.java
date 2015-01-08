package com.sirma.itt.javacourse.chatcommon.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Holds utility methods for validating client input.
 * 
 * @author smustafov
 */
public final class Validator {

	private static final String NICKNAME_REG_EX = "[\\w\\d-]+";
	private static final String WHITESPACES_REG_EX = "[\\s]*";
	private static final Pattern NICKNAME_PATTERN = Pattern.compile(NICKNAME_REG_EX,
			Pattern.CASE_INSENSITIVE);
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile(WHITESPACES_REG_EX,
			Pattern.CASE_INSENSITIVE);

	public static final int MAX_NICKNAME_LENGHT = 15;

	/**
	 * Protects from instantiation.
	 */
	private Validator() {

	}

	/**
	 * Checks if given nickname is valid. Valid nickname can consist of - letters, numbers, dash,
	 * underscore.
	 * 
	 * @param nickname
	 *            - the nickname to be checked if its valid
	 * @return - true if the given nickname is valid, otherwise false
	 */
	public static boolean isValidNickname(String nickname) {
		Matcher matcher = NICKNAME_PATTERN.matcher(nickname);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if given chat message contains whitespaces.
	 * 
	 * @param message
	 *            - the message to be checked if it is empty
	 * @return - true if the give message contains whitespaces, otherwise false
	 */
	public static boolean isWhitespaceMessage(String message) {
		Matcher matcher = WHITESPACE_PATTERN.matcher(message);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * Capitalizes the first letter of given string and returns it.
	 * 
	 * @param message
	 *            - the string which first letter to be capitalized
	 * @return - the string with capitalized first letter
	 */
	public static String capitalizeFirstLetter(String message) {
		if (message.length() == 0) {
			return message;
		}
		// return message.substring(0, 1).toUpperCase() + message.substring(1);
		return Character.toUpperCase(message.charAt(0)) + message.substring(1);
	}

}
