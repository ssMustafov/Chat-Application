package com.sirma.itt.javacourse.chatclient.commands;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;

/**
 * Handles the LoggedIn query from the server. Sent from the server to the newly connected client.
 * 
 * @author Sinan
 */
public class LoggedIn extends ClientCommand {

	private Query query;

	/**
	 * Creates a new logged in command with given {@link View} of the client and server
	 * {@link Query}.
	 * 
	 * @param view
	 *            - the view of the client
	 * @param query
	 *            - the query sent from the server
	 */
	public LoggedIn(View view, Query query) {
		super(view);
		this.query = query;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		System.out.println(query.toString());
		getClientView().appendMessageToChatArea(query.getMessage());
	}

}
