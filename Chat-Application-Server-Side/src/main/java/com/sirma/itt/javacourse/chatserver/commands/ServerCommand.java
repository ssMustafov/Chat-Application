package com.sirma.itt.javacourse.chatserver.commands;

import com.sirma.itt.javacourse.chatserver.server.Client;
import com.sirma.itt.javacourse.chatserver.server.ServerDispatcher;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Server command handles client queries.
 * 
 * @author Sinan
 */
public abstract class ServerCommand {

	private View view;
	private ServerDispatcher serverDispatcher;

	/**
	 * Returns the {@link ServerDispatcher}.
	 * 
	 * @return - the server dispatcher
	 */
	protected ServerDispatcher getServerDispatcher() {
		return serverDispatcher;
	}

	/**
	 * Returns the {@link View}.
	 * 
	 * @return - the server view
	 */
	protected View getServerView() {
		return view;
	}

	/**
	 * Creates a new server command with given {@link ServerDispatcher}.
	 * 
	 * @param serverDispatcher
	 *            - the server dispatcher
	 * @param view
	 *            - the view of the server
	 */
	public ServerCommand(ServerDispatcher serverDispatcher, View view) {
		this.serverDispatcher = serverDispatcher;
		this.view = view;
	}

	/**
	 * Executes the query of the given {@link Client}.
	 * 
	 * @param client
	 *            - the client which query will be executed
	 */
	public abstract void execute(Client client);
}
