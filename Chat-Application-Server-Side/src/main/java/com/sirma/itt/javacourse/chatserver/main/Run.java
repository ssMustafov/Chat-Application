package com.sirma.itt.javacourse.chatserver.main;

import javax.swing.SwingUtilities;

import com.sirma.itt.javacourse.chatserver.views.ServerView;

/**
 * Runs the server of the chat application.
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
				new ServerView();
			}
		});
	}

}
