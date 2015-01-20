package com.sirma.itt.javacourse.chatclient.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests {@link MementoCaretaker} class.
 * 
 * @author Sinan
 */
public class MementoCaretakerTest {

	/**
	 * Tests {@link MementoCaretaker#getNextMemento()} by adding one message.
	 */
	@Test
	public void testGetNextMementoWithOneMessage() {
		MementoCaretaker mementos = new MementoCaretaker();
		mementos.addMemento(new MessageMemento("hi"));

		assertEquals("hi", mementos.getNextMemento().getMessage());
	}

	/**
	 * Tests {@link MementoCaretaker#getNextMemento()} by adding two messages and trying to get
	 * after the last message.
	 */
	@Test
	public void testGetNextMementoBoundary() {
		MementoCaretaker mementos = new MementoCaretaker();
		mementos.addMemento(new MessageMemento("hi"));
		mementos.addMemento(new MessageMemento("spring"));

		assertEquals("spring", mementos.getNextMemento().getMessage());
		assertEquals("hi", mementos.getNextMemento().getMessage());
		assertEquals("hi", mementos.getNextMemento().getMessage());
		assertEquals("hi", mementos.getNextMemento().getMessage());
	}

	/**
	 * Tests {@link MementoCaretaker#getNextMemento()} by adding two messages and getting them
	 * consequently.
	 */
	@Test
	public void testGetNextMementoWithTwoMessages() {
		MementoCaretaker mementos = new MementoCaretaker();
		mementos.addMemento(new MessageMemento("apple"));
		mementos.addMemento(new MessageMemento("hi"));

		assertEquals("hi", mementos.getNextMemento().getMessage());
		assertEquals("apple", mementos.getNextMemento().getMessage());
	}

	/**
	 * Tests {@link MementoCaretaker#getNextMemento()} by adding four messages and getting them
	 * consequently.
	 */
	@Test
	public void testGetNextMementoWithFiveMessages() {
		MementoCaretaker mementos = new MementoCaretaker();
		mementos.addMemento(new MessageMemento("apple"));
		mementos.addMemento(new MessageMemento("hi"));
		mementos.addMemento(new MessageMemento("recursion"));
		mementos.addMemento(new MessageMemento("Sharks are sharks"));
		mementos.addMemento(new MessageMemento("Today's future depend on us"));

		assertEquals("Today's future depend on us", mementos.getNextMemento().getMessage());
		assertEquals("Sharks are sharks", mementos.getNextMemento().getMessage());
		assertEquals("recursion", mementos.getNextMemento().getMessage());
		assertEquals("hi", mementos.getNextMemento().getMessage());
		assertEquals("apple", mementos.getNextMemento().getMessage());
	}

	/**
	 * Tests {@link MementoCaretaker#getPreviousMemento()} by adding one message.
	 */
	@Test
	public void testGetPreviousMementoWithOneMessage() {
		MementoCaretaker mementos = new MementoCaretaker();
		mementos.addMemento(new MessageMemento("hi"));

		assertEquals("hi", mementos.getPreviousMemento().getMessage());
	}

	/**
	 * Tests {@link MementoCaretaker#getPreviousMemento()} by adding two messages and getting them
	 * consequently after the end of the mementos.
	 */
	@Test
	public void testGetPreviousMementoBoundary() {
		MementoCaretaker mementos = new MementoCaretaker();
		mementos.addMemento(new MessageMemento("hi"));
		mementos.addMemento(new MessageMemento("spring"));

		assertEquals("spring", mementos.getNextMemento().getMessage());
		assertEquals("hi", mementos.getNextMemento().getMessage());
		assertEquals("spring", mementos.getPreviousMemento().getMessage());
		assertEquals("spring", mementos.getPreviousMemento().getMessage());
		assertEquals("spring", mementos.getPreviousMemento().getMessage());
	}

	/**
	 * Tests {@link MementoCaretaker#getPreviousMemento()} by adding two messages and getting them
	 * consequently.
	 */
	@Test
	public void testGetPreviousMementoWithTwoMessages() {
		MementoCaretaker mementos = new MementoCaretaker();
		mementos.addMemento(new MessageMemento("hi"));
		mementos.addMemento(new MessageMemento("collection"));

		assertEquals("collection", mementos.getPreviousMemento().getMessage());
		assertEquals("collection", mementos.getPreviousMemento().getMessage());
	}

	/**
	 * Tests {@link MementoCaretaker}.
	 */
	@Test
	public void testHalfIteration() {
		MementoCaretaker mementos = new MementoCaretaker();
		mementos.addMemento(new MessageMemento("hi"));
		mementos.addMemento(new MessageMemento("collection"));
		mementos.addMemento(new MessageMemento("test"));
		mementos.addMemento(new MessageMemento("Message"));

		assertEquals("Message", mementos.getNextMemento().getMessage());
		assertEquals("test", mementos.getNextMemento().getMessage());
		assertEquals("Message", mementos.getPreviousMemento().getMessage());
		assertEquals("test", mementos.getNextMemento().getMessage());
		assertEquals("collection", mementos.getNextMemento().getMessage());
		assertEquals("test", mementos.getPreviousMemento().getMessage());
	}

	/**
	 * Tests {@link MementoCaretaker#getPreviousMemento()} by adding four messages and getting them
	 * consequently to the beginning then consequently to the end.
	 */
	@Test
	public void testFullIteration() {
		MementoCaretaker mementos = new MementoCaretaker();
		mementos.addMemento(new MessageMemento("hi"));
		mementos.addMemento(new MessageMemento("collection"));
		mementos.addMemento(new MessageMemento("test"));
		mementos.addMemento(new MessageMemento("Message"));

		assertEquals("Message", mementos.getNextMemento().getMessage());
		assertEquals("test", mementos.getNextMemento().getMessage());
		assertEquals("collection", mementos.getNextMemento().getMessage());
		assertEquals("hi", mementos.getNextMemento().getMessage());
		assertEquals("hi", mementos.getNextMemento().getMessage());
		assertEquals("collection", mementos.getPreviousMemento().getMessage());
		assertEquals("test", mementos.getPreviousMemento().getMessage());
		assertEquals("Message", mementos.getPreviousMemento().getMessage());
	}

}
