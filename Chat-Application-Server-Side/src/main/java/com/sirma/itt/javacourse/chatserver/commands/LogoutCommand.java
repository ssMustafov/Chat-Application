package com.sirma.itt.javacourse.chatserver.commands;

import java.util.ResourceBundle;

import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageConstants;
import com.sirma.itt.javacourse.chatserver.server.Client;
import com.sirma.itt.javacourse.chatserver.server.ServerManager;
import com.sirma.itt.javacourse.chatserver.server.SocketsManager;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Hanles {@link QueryTypes#Logout} query.
 * 
 * @author Sinan
 */
public class LogoutCommand extends ServerCommand {

	private ResourceBundle bundle = LanguageBundleSingleton.getServerBundleInstance();

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
	 */
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
			handler.sendQuery(new Query(QueryTypes.Refused,
					LanguageConstants.CLIENT_NOT_LOGGED_IN_MESSAGE));
			return;
		}

		getServerManager().removeClient(client);
		getSocketsManager().remove(client.getId());
		handler.sendQuery(new Query(QueryTypes.LoggedOut, LanguageConstants.CLIENT_LOGOUT_MESSAGE));

		String clientDisconnectedMessage = String.format("@%s %s", client.getNickname(),
				bundle.getString(LanguageConstants.SERVER_CLIENT_DISCONNECTED_MESSAGE));
		getView().appendMessageToConsole(clientDisconnectedMessage);
		getView().removeOnlineClient(client.getNickname());

		getServerManager().dispatchQueryToAll(
				new Query(QueryTypes.ClientDisconnected, client.getNickname()));
	}
}
