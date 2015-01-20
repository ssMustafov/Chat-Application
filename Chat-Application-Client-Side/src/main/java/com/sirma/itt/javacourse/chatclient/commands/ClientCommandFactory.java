package com.sirma.itt.javacourse.chatclient.commands;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;

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

			case Closed:
				// command = new ClosedCommand(view, query);
				break;

			default:
				throw new IllegalArgumentException("Not supported command");
		}

		return command;
	}

}
