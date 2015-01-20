package com.sirma.itt.javacourse.chatclient.commands;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;

/**
 * Holds method for creating {@link ClientCommand}s.
 * 
 * @author smustafov
 */
public final class ClientCommandFactory {

	/**
	 * Protects from instantiation.
	 */
	private ClientCommandFactory() {

	}

	/**
	 * Creates a new {@link ClientCommand} with given view of the client and {@link Query}.
	 * 
	 * @param view
	 *            - the view of the client
	 * @param query
	 *            - the query on which the command is depending
	 * @return - client command handler depending on the query type
	 */
	public static ClientCommand createCommand(View view, Query query) {
		ClientCommand command = null;

		switch (query.getQueryType()) {

			case LoggedIn:
				command = new LoggedIn(view, query);
				break;

			case LoggedOut:
				command = new LoggedOutCommand(view);
				break;

			case ClientsNicknames:
				command = new ClientsNicknames(view, query);
				break;

			default:
				throw new IllegalArgumentException("Not supported command: " + query.getQueryType());
		}

		return command;
	}

}
