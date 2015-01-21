package com.sirma.itt.javacourse.chatserver.commands;

import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageConstants;
import com.sirma.itt.javacourse.chatcommon.utils.Validator;
import com.sirma.itt.javacourse.chatserver.server.Client;
import com.sirma.itt.javacourse.chatserver.server.ServerManager;
import com.sirma.itt.javacourse.chatserver.server.SocketsManager;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Handles {@link QueryTypes#SendMessage} query.
 * 
 * @author Sinan
 */
public class SendMessageCommand extends ServerCommand {

	private Query query;

	/**
	 * Creates a new login command with given {@link ServerManager}, {@link SocketsManager},
	 * {@link View} and {@link Query}.
	 * 
	 * @param serverManager
	 *            - the server's manager
	 * @param socketsManager
	 *            - the sockets manager
	 * @param view
	 *            - the server's view
	 * @param query
	 *            - the client's query
	 */
	public SendMessageCommand(ServerManager serverManager, SocketsManager socketsManager,
			View view, Query query) {
		super(serverManager, socketsManager, view);
		this.query = query;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Client client) {
		QueryHandler handler = getSocketsManager().getHandler(client.getId());
		if (!getServerManager().containsClient(client.getNickname())) {
			handler.sendQuery(new Query(QueryTypes.Refused,
					LanguageConstants.CLIENT_NOT_LOGGED_IN_MESSAGE));
			return;
		}

		String message = query.getMessage();
		String capitalizedMessage = Validator.capitalizeFirstLetter(message);
		String formattedMessage = String.format("<%s>: %s", client.getNickname(),
				capitalizedMessage);
		getServerManager().dispatchQueryToAll(new Query(QueryTypes.SentMessage, formattedMessage));
	}

}
