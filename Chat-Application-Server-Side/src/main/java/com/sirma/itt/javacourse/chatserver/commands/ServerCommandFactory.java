package com.sirma.itt.javacourse.chatserver.commands;

import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatserver.server.ServerManager;
import com.sirma.itt.javacourse.chatserver.server.SocketsManager;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Factory for creating {@link ServerCommand}s. These commands are handled by the server and sent
 * from the clients.
 * 
 * @author Sinan
 */
public final class ServerCommandFactory {

	/**
	 * Protects from instantiation.
	 */
	private ServerCommandFactory() {

	}

	/**
	 * Creates a {@link ServerCommand}.
	 * 
	 * @param serverManager
	 *            - the server's manager
	 * @param socketsManager
	 *            - the sockets manager
	 * @param view
	 *            - the view of the server
	 * @param query
	 *            - the client's query
	 * @return - server command depending on the query type
	 */
	public static ServerCommand createCommand(ServerManager serverManager,
			SocketsManager socketsManager, View view, Query query) {
		ServerCommand command = null;

		switch (query.getQueryType()) {
			case Login:
				command = new LoginCommand(serverManager, socketsManager, view, query);
				break;

			case Logout:
				command = new LogoutCommand(serverManager, socketsManager, view);
				break;

			case SendMessage:
				command = new SendMessageCommand(serverManager, socketsManager, view, query);
				break;

			default:
				throw new IllegalArgumentException("Not supported command: " + query.getQueryType());
		}

		return command;
	}

}
