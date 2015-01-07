package com.sirma.itt.javacourse.chatserver.server;

import com.sirma.itt.javacourse.chatcommon.utils.Query;
import com.sirma.itt.javacourse.chatserver.commands.ServerCommand;
import com.sirma.itt.javacourse.chatserver.commands.ServerCommandFactory;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Reads queries from the client. Every client has this thread working for him.
 * 
 * @author Sinan
 */
public class ClientReaderThread extends Thread {

	private ServerDispatcher serverDispatcher;
	private Client client;
	private View view;

	/**
	 * Creates a new client reader with given server dispatcher, client and view of the server.
	 * 
	 * @param serverDispatcher
	 *            - the server dispatcher
	 * @param client
	 *            - the client on which this thread will read queries
	 * @param view
	 *            - the view of the server
	 */
	public ClientReaderThread(ServerDispatcher serverDispatcher, Client client, View view) {
		this.serverDispatcher = serverDispatcher;
		this.client = client;
		this.view = view;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		Query query = null;
		while ((query = client.readQuery()) != null) {
			handleClientQuery(query);
		}

		interrupt();
	}

	/**
	 * Handles client query by giving the query to the {@link ServerCommand}.
	 * 
	 * @param query
	 *            - the client query that will be read
	 */
	private void handleClientQuery(Query query) {
		ServerCommand command = ServerCommandFactory.createCommand(serverDispatcher, view, query);
		command.execute(client);
	}
}
