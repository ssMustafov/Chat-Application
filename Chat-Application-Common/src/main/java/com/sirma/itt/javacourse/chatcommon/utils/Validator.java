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
	private static final Pattern NICKNAME_PATTERN = Pattern.compile(NICKNAME_REG_EX,
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

}
