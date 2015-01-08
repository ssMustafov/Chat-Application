package com.sirma.itt.javacourse.chatclient.views;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Overrides the {@link PlainDocument}'s
 * {@link PlainDocument#insertString(int, String, AttributeSet)} and
 * {@link PlainDocument#replace(int, int, String, AttributeSet)}. It limits inserted string's length
 * to given maximum length.
 * 
 * @author smustafov
 */
public class DocumentLengthFilter extends PlainDocument {

	private static final long serialVersionUID = -6062372949132652579L;
	private int maxLength;

	/**
	 * Creates a new document length filter with given max length.
	 * 
	 * @param maxLength
	 *            - the max length of the string in the document
	 */
	public DocumentLengthFilter(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * Limits the inserted string's length to the given max length. {@inheritDoc}
	 */
	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		// This rejects the entire insertion if it would make
		// the contents too long.
		if ((getLength() + str.length()) <= maxLength) {
			super.insertString(offset, str, attr);
		}
	}

	/**
	 * Limits the replaced string's length to the given max length. {@inheritDoc}
	 */
	@Override
	public void replace(int offset, int length, String str, AttributeSet attrs)
			throws BadLocationException {
		// This rejects the entire replacement if it would make
		// the contents too long.
		if ((getLength() + str.length() - length) <= maxLength) {
			super.replace(offset, length, str, attrs);
		}
	}
}
