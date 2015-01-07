package com.sirma.itt.javacourse.chatclient.commands;

import com.sirma.itt.javacourse.chatclient.views.View;

/**
 * Handles {@link Query} - queries sent from the server.
 * 
 * @author smustafov
 */
public abstract class ClientCommand {
	private View view;

	/**
	 * Creates a new client command with given view of the client.
	 * 
	 * @param view
	 *            - the view of the client
	 */
	public ClientCommand(View view) {
		this.view = view;
	}

	/**
	 * Returns the {@link View}.
	 * 
	 * @return - the client view
	 */
	protected View getClientView() {
		return view;
	}

	/**
	 * Executes a {@link Query}.
	 */
	public abstract void execute();
}
