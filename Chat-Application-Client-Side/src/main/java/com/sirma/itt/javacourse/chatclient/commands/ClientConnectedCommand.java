package com.sirma.itt.javacourse.chatclient.commands;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;

/**
 * Handles the ClientConnected query from the server. Sent from the server when a new client is
 * connected.
 * 
 * @author Sinan
 */
public class ClientConnectedCommand extends ClientCommand {

	private Query query;

	/**
	 * Creates a new client connected handler with given view of the client and {@link Query} sent
	 * from the server.
	 * 
	 * @param view
	 *            - the view of the client
	 * @param query
	 *            - the query sent from the server
	 */
	public ClientConnectedCommand(View view, Query query) {
		super(view);
		this.query = query;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		String nickname = query.getMessage();
		getClientView().addOnlineClient(nickname);
	}

}
