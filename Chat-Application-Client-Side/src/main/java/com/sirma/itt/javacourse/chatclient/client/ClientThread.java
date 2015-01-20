package com.sirma.itt.javacourse.chatclient.client;

import java.io.IOException;

import com.sirma.itt.javacourse.chatclient.commands.ClientCommand;
import com.sirma.itt.javacourse.chatclient.commands.ClientCommandFactory;
import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;

/**
 * @author Sinan
 */
public class ClientThread implements Runnable {

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
			e.printStackTrace();
		}

		queryHandler.closeStreams();
	}

	/**
	 * Starts this thread.
	 */
	public void start() {
		isRunning = true;
		new Thread(this).start();
	}

	/**
	 * Stops this thread.
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
