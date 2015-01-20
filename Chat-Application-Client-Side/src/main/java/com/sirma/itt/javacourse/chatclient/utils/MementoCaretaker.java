package com.sirma.itt.javacourse.chatclient.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Holds a {@code List} of {@code MessageMemento}s. This class is part of the Memento design
 * pattern.
 * 
 * @author Sinan
 */
public class MementoCaretaker {
	private static final int MAX_SIZE = 10;
	private List<MessageMemento> savedMementos = new LinkedList<>();
	private int index = -1;

	/**
	 * Adds new memento to the list.
	 * 
	 * @param memento
	 *            - the memento to be added to the list
	 */
	public void addMemento(MessageMemento memento) {
		savedMementos.add(memento);
		index = -1;

		if (savedMementos.size() == MAX_SIZE) {
			savedMementos.remove(0);
		}
	}

	/**
	 * Returns a memento at given index from the list.
	 * 
	 * @param index
	 *            - the index of the memento
	 * @return - the memento at given index from the list.
	 */
	public MessageMemento getMemento(int index) {
		return savedMementos.get(index);
	}

	/**
	 * Returns the next memento from the list.
	 * 
	 * @return - the next memento from the list
	 */
	public MessageMemento getNextMemento() {
		MessageMemento memento = null;
		index++;
		if (index >= savedMementos.size()) {
			index = savedMementos.size() - 1;
		}
		if (index <= savedMementos.size() - 1) {
			memento = savedMementos.get(savedMementos.size() - 1 - index);
		}
		return memento;
	}

	/**
	 * Returns the previous memento from the list.
	 * 
	 * @return - the previous memento from the list
	 */
	public MessageMemento getPreviousMemento() {
		MessageMemento memento = null;

		if ((savedMementos.size() - index) >= savedMementos.size() - 1) {
			index = 1;
		}
		if (index >= 0) {
			memento = savedMementos.get(savedMementos.size() - index);
		}
		index--;
		if (index < 0) {
			index = 0;
		}
		return memento;
	}
}
