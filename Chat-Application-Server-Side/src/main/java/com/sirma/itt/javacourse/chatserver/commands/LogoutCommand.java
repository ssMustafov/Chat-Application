package com.sirma.itt.javacourse.chatserver.commands;

import java.util.ResourceBundle;

import com.sirma.itt.javacourse.chatcommon.models.Client;
import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;
import com.sirma.itt.javacourse.chatcommon.utils.ServerLanguageConstants;
import com.sirma.itt.javacourse.chatserver.server.ServerManager;
import com.sirma.itt.javacourse.chatserver.server.SocketsManager;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * @author Sinan
 */
public class LogoutCommand extends ServerCommand {

	private ResourceBundle bundle = LanguageBundleSingleton.getServerBundleInstance();

	public LogoutCommand(ServerManager serverManager, SocketsManager socketsManager, View view) {
		super(serverManager, socketsManager, view);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Client client) {
		QueryHandler handler = getSocketsManager().getHandler(client.getId());
		if (!getServerManager().containsClient(client.getNickname())) {
			handler.sendQuery(new Query(QueryTypes.Refused, "You are not logged in"));
			return;
		}

		getServerManager().removeClient(client);
		handler.sendQuery(new Query(QueryTypes.LoggedOut, "You have logged out"));

		String clientDisconnectedMessage = String.format("@%s %s", client.getNickname(),
				bundle.getString(ServerLanguageConstants.CLIENT_DISCONNECTED_MESSAGE));
		getView().appendMessageToConsole(clientDisconnectedMessage);
		getView().removeOnlineClient(client.getNickname());

		getServerManager().dispatchQueryToAll(
				new Query(QueryTypes.ClientDisconnected, client.getNickname()));
	}
}
