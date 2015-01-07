package com.sirma.itt.javacourse.chatclient.client;

import com.sirma.itt.javacourse.chatclient.commands.ClientCommand;
import com.sirma.itt.javacourse.chatclient.commands.ClientCommandFactory;
import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.utils.Query;
import com.sirma.itt.javacourse.chatcommon.utils.QueryHandler;

/**
 * Reads {@link Query} - queries sent from the server.
 * 
 * @author Sinan
 */
public class ClientThread extends Thread {

	private QueryHandler queryHandler;
	private View view;

	/**
	 * Creates a new client thread with given query handler and view of the client.
	 * 
	 * @param queryHandler
	 *            - the query handler
	 * @param view
	 *            - the view of the client
	 */
	public ClientThread(QueryHandler queryHandler, View view) {
		this.queryHandler = queryHandler;
		this.view = view;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		Query query = null;
		while ((query = queryHandler.readQuery()) != null) {
			handleServerQuery(query);
		}

		queryHandler.closeStreams();
		interrupt();
	}

	/**
	 * Handles the queries sent from the server.
	 * 
	 * @param query
	 *            - the {@link Query} sent from the server
	 */
	private void handleServerQuery(Query query) {
		ClientCommand command = ClientCommandFactory.createCommand(view, query);
		command.execute();
	}

}
