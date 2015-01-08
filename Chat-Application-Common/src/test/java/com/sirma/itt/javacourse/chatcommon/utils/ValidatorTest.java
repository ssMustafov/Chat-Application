package com.sirma.itt.javacourse.chatcommon.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests {@link com.sirma.itt.javacourse.chatcommon.utils.Validator} class.
 * 
 * @author smustafov
 */
public class ValidatorTest {

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatcommon.utils.Validator#isValidNickname(java.lang.String)}
	 * with series of valid nicknames.
	 */
	@Test
	public void testSeriesOfValidNicknames() {
		String nickname1 = "test-619";
		String nickname2 = "cold";
		String nickname3 = "down_ToWn087";

		assertTrue(Validator.isValidNickname(nickname1));
		assertTrue(Validator.isValidNickname(nickname2));
		assertTrue(Validator.isValidNickname(nickname3));
	}

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatcommon.utils.Validator#isValidNickname(java.lang.String)}
	 * with series of invalid nicknames.
	 */
	@Test
	public void testSeriesOfInvalidNicknames() {
		String nickname1 = " ";
		String nickname2 = "";
		String nickname3 = "down^town@%$^";

		assertFalse(Validator.isValidNickname(nickname1));
		assertFalse(Validator.isValidNickname(nickname2));
		assertFalse(Validator.isValidNickname(nickname3));
	}

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatcommon.utils.Validator#isWhitespaceMessage(java.lang.String)}
	 * with whitespace messages.
	 */
	@Test
	public void testWithWhitespaceMessage() {
		String message1 = " ";
		String message2 = "		";

		assertTrue(Validator.isWhitespaceMessage(message1));
		assertTrue(Validator.isWhitespaceMessage(message2));
	}

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatcommon.utils.Validator#isWhitespaceMessage(java.lang.String)}
	 * with not whitespace messages.
	 */
	@Test
	public void testWithNotWhitespaceMessage() {
		String message1 = "calm";
		String message2 = "p&lea-^se";

		assertFalse(Validator.isWhitespaceMessage(message1));
		assertFalse(Validator.isWhitespaceMessage(message2));
	}

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatcommon.utils.Validator#capitalizeFirstLetter(java.lang.String)}
	 * with empty message.
	 */
	@Test
	public void testCapitalizeFirstLetterWithEmptyMessage() {
		String message = "";
		String actual = Validator.capitalizeFirstLetter(message);

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
		String actual = Validator.capitalizeFirstLetter(message);
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
		String actual = Validator.capitalizeFirstLetter(message);
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
		String actual = Validator.capitalizeFirstLetter(message);

		assertEquals(message, actual);
	}

}
