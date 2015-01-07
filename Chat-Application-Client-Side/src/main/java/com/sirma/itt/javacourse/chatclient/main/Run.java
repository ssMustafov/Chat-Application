package com.sirma.itt.javacourse.chatclient.main;

import javax.swing.SwingUtilities;

import com.sirma.itt.javacourse.chatclient.views.LoginForm;

/**
 * Starts a new client for the chat application.
 * 
 * @author Sinan
 */
public final class Run {

	/**
	 * Protects from instantiation.
	 */
	private Run() {

	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            - arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new LoginForm();
			}
		});
	}
}
