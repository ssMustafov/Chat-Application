package com.sirma.itt.javacourse.chatclient.commands;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.utils.Query;
import com.sirma.itt.javacourse.chatcommon.utils.QueryTypes;

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
	 * @return - client command handler depending on the {@link QueryTypes}
	 */
	public static ClientCommand createCommand(View view, Query query) {
		ClientCommand command = null;

		switch (query.getQueryType()) {
			case Success:
				command = new SuccesCommand(view, query);
				break;
			case SentMessage:
				command = new SentMessageCommand(view, query);
				break;
			case ClientConnected:
				command = new ClientConnectedCommand(view, query);
				break;
			case ClientDisconnected:
				command = new ClientDisconnectedCommand(view, query);
				break;
			case LoggedIn:
				command = new LoggedInCommand(view, query);
				break;

			default:
				throw new IllegalArgumentException("Not supported command");
		}

		return command;
	}

}
