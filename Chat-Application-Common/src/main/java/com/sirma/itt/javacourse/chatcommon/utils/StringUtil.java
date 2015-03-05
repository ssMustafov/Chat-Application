package com.sirma.itt.javacourse.chatcommon.utils;

/**
 * Holds utility methods for working with strings.
 * 
 * @author Sinan
 */
public final class StringUtil {

	/**
	 * Protects from instantiation.
	 */
	private StringUtil() {

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

		return Character.toUpperCase(message.charAt(0)) + message.substring(1);
	}

}
