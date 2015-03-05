package com.sirma.itt.javacourse.chatcommon.utils;

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

}
