package com.sirma.itt.javacourse.chatserver.commands;

import com.sirma.itt.javacourse.chatcommon.models.Client;
import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;
import com.sirma.itt.javacourse.chatcommon.utils.Validator;
import com.sirma.itt.javacourse.chatserver.server.ServerManager;
import com.sirma.itt.javacourse.chatserver.server.SocketsManager;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * @author Sinan
 */
public class SendMessageCommand extends ServerCommand {

	private Query query;

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
			handler.sendQuery(new Query(QueryTypes.Refused, "You're not logged in"));
			return;
		}

		String message = query.getMessage();
		String capitalizedMessage = Validator.capitalizeFirstLetter(message);
		String formattedMessage = String.format("<%s>: %s", client.getNickname(),
				capitalizedMessage);
		getServerManager().dispatchQueryToAll(new Query(QueryTypes.SentMessage, formattedMessage));
	}

}
