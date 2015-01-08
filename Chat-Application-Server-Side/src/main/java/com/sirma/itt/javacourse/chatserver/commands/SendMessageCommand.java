package com.sirma.itt.javacourse.chatserver.commands;

import com.sirma.itt.javacourse.chatcommon.utils.Query;
import com.sirma.itt.javacourse.chatcommon.utils.QueryTypes;
import com.sirma.itt.javacourse.chatcommon.utils.Validator;
import com.sirma.itt.javacourse.chatserver.server.Client;
import com.sirma.itt.javacourse.chatserver.server.ServerDispatcher;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Handles queries when client sends message.
 * 
 * @author Sinan
 */
public class SendMessageCommand extends ServerCommand {

	private Query clientQuery;

	/**
	 * Creates a new send message command.
	 * 
	 * @param serverDispatcher
	 *            - the server dispatcher
	 * @param view
	 *            - the view of the server
	 * @param clientQuery
	 *            - client's query
	 */
	public SendMessageCommand(ServerDispatcher serverDispatcher, View view, Query clientQuery) {
		super(serverDispatcher, view);
		this.clientQuery = clientQuery;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Client client) {
		String clientMessage = clientQuery.getMessage();
		String capitalizedMessage = Validator.capitalizeFirstLetter(clientMessage);

		String formattedMessage = String.format("<%s>: %s", client.getNickname(),
				capitalizedMessage);
		getServerDispatcher().dispatchQuery(new Query(QueryTypes.SentMessage, formattedMessage));
	}

}
