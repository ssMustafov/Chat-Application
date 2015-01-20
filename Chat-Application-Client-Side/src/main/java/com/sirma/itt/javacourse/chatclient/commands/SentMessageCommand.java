package com.sirma.itt.javacourse.chatclient.commands;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;

/**
 * Handles the SentMessage query from the server. Sent from the server when a client sends a
 * message.
 * 
 * @author Sinan
 */
public class SentMessageCommand extends ClientCommand {

	private Query query;

	/**
	 * Creates a new sent message command with given {@link View} of the client and server
	 * {@link Query}.
	 * 
	 * @param view
	 *            - the view of the client
	 * @param query
	 *            - the query sent from the client
	 */
	public SentMessageCommand(View view, Query query) {
		super(view);
		this.query = query;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		String message = query.getMessage();
		getClientView().appendMessageToChatArea(message);
	}

}
