package com.sirma.itt.javacourse.chatserver.commands;

import com.sirma.itt.javacourse.chatserver.server.Client;
import com.sirma.itt.javacourse.chatserver.server.ServerManager;
import com.sirma.itt.javacourse.chatserver.server.SocketsManager;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Handles client queries.
 * 
 * @author Sinan
 */
public abstract class ServerCommand {
	private View view;
	private ServerManager serverManager;
	private SocketsManager socketsManager;

	/**
	 * Creates a new server command with given {@link ServerManager}, {@link SocketsManager} and
	 * {@link View}.
	 * 
	 * @param serverManager
	 *            - the server's manager
	 * @param socketsManager
	 *            - the sockets manager
	 * @param view
	 *            - the server's view
	 */
	public ServerCommand(ServerManager serverManager, SocketsManager socketsManager, View view) {
		this.serverManager = serverManager;
		this.socketsManager = socketsManager;
		this.view = view;
	}

	/**
	 * Returns the {@link View}.
	 * 
	 * @return - the server view
	 */
	protected View getView() {
		return view;
	}

	/**
	 * Returns the {@link ServerManager}.
	 * 
	 * @return - the server's manager
	 */
	protected ServerManager getServerManager() {
		return serverManager;
	}

	/**
	 * Returns the {@link SocketsManager}.
	 * 
	 * @return - the sockets manager
	 */
	protected SocketsManager getSocketsManager() {
		return socketsManager;
	}

	/**
	 * Executes the query of the given {@link Client}.
	 * 
	 * @param client
	 *            - the client which query will be executed
	 */
	public abstract void execute(Client client);

}
