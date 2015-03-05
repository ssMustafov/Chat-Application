package com.sirma.itt.javacourse.chatcommon.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests {@link com.sirma.itt.javacourse.chatcommon.utils.StringUtil} class.
 * 
 * @author Sinan
 */
public class StringUtilTest {

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatcommon.utils.Validator#capitalizeFirstLetter(java.lang.String)}
	 * with empty message.
	 */
	@Test
	public void testCapitalizeFirstLetterWithEmptyMessage() {
		String message = "";
		String actual = StringUtil.capitalizeFirstLetter(message);

		assertEquals(message, actual);
	}

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatcommon.utils.Validator#capitalizeFirstLetter(java.lang.String)}
	 * with uncapitalized first letter message.
	 */
	@Test
	public void testCapitalizeFirstLetterWithUncapitalizedMessage() {
		String message = "doley";
		String actual = StringUtil.capitalizeFirstLetter(message);
		String expected = "Doley";

		assertEquals(expected, actual);
	}

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatcommon.utils.Validator#capitalizeFirstLetter(java.lang.String)}
	 * with uncapitalized first letter and long message.
	 */
	@Test
	public void testCapitalizeFirstLetterWithUncapitalizedLongMessage() {
		String message = "this release includes important security fixes. Oracle strongly recommends that all Java SE 8 users upgrade to this release.";
		String actual = StringUtil.capitalizeFirstLetter(message);
		String expected = "This release includes important security fixes. Oracle strongly recommends that all Java SE 8 users upgrade to this release.";

		assertEquals(expected, actual);
	}

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatcommon.utils.Validator#capitalizeFirstLetter(java.lang.String)}
	 * with already capitalized first letter message.
	 */
	@Test
	public void testCapitalizeFirstLetterWithCapitalizedMessage() {
		String message = "Start";
		String actual = StringUtil.capitalizeFirstLetter(message);

		assertEquals(message, actual);
	}

}
