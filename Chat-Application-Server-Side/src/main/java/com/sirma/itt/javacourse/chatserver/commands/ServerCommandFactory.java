package com.sirma.itt.javacourse.chatserver.commands;

import com.sirma.itt.javacourse.chatcommon.utils.Query;
import com.sirma.itt.javacourse.chatserver.server.ServerDispatcher;
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
	 * Creates a {@link ServerCommand} with given {@link ServerDispatcher} and {@link Query}.
	 * 
	 * @param serverDispatcher
	 *            - the server dispatcher
	 * @param view
	 *            - the view of the server
	 * @param query
	 *            - the client's query
	 * @return - server command depending on the query type
	 */
	public static ServerCommand createCommand(ServerDispatcher serverDispatcher, View view,
			Query query) {
		ServerCommand command = null;

		switch (query.getQueryType()) {
			case Login:
				command = new LoginCommand(serverDispatcher, view, query);
				break;

			case Logout:
				command = new LogoutCommand(serverDispatcher, view);
				break;

			case SendMessage:
				command = new SendMessageCommand(serverDispatcher, view, query);
				break;

			default:
				throw new IllegalArgumentException("Not supported command");
		}

		return command;
	}

}
