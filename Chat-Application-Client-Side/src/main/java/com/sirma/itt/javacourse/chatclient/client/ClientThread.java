package com.sirma.itt.javacourse.chatclient.client;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sirma.itt.javacourse.chatclient.commands.ClientCommand;
import com.sirma.itt.javacourse.chatclient.commands.ClientCommandFactory;
import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;

/**
 * For every client is created this thread for reading {@link Query}s from the server.
 * 
 * @author Sinan
 */
public class ClientThread implements Runnable {

	private static final Logger LOGGER = LogManager.getLogger(ClientThread.class);
	private QueryHandler queryHandler;
	private View view;
	private boolean isRunning = false;

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
		try {
			while ((query = queryHandler.readQuery()) != null) {
				if (!isRunning) {
					break;
				}
				if (query.getQueryType() != QueryTypes.Alive) {
					handleServerQuery(query);
				}
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

		queryHandler.closeStreams();
	}

	/**
	 * Starts the clients thread.
	 */
	public void start() {
		isRunning = true;
		new Thread(this).start();
	}

	/**
	 * Stops the clients thread.
	 */
	public void stop() {
		isRunning = false;
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
