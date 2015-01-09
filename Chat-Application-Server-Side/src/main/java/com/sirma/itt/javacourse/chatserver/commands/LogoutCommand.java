package com.sirma.itt.javacourse.chatserver.commands;

import java.util.ResourceBundle;

import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;
import com.sirma.itt.javacourse.chatcommon.utils.Query;
import com.sirma.itt.javacourse.chatcommon.utils.QueryTypes;
import com.sirma.itt.javacourse.chatserver.server.Client;
import com.sirma.itt.javacourse.chatserver.server.ServerDispatcher;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * @author Sinan
 */
public class LogoutCommand extends ServerCommand {

	private ResourceBundle bundle = LanguageBundleSingleton.getServerBundleInstance();

	/**
	 * @param serverDispatcher
	 *            - the
	 * @param view
	 *            - the
	 */
	public LogoutCommand(ServerDispatcher serverDispatcher, View view) {
		super(serverDispatcher, view);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Client client) {
		if (!getServerDispatcher().containsClient(client.getNickname())) {
			client.sendQuery(new Query(QueryTypes.Refused, "You're not logged in"));
			return;
		}

		getServerDispatcher().removeClient(client);
		client.sendQuery(new Query(QueryTypes.Success, "You have logged out"));

		String clientDisconnectedMessage = String.format("%s%s %s", "@", client.getNickname(),
				bundle.getString("clientDisconnected"));
		getServerView().appendMessageToConsole(clientDisconnectedMessage);
		getServerView().removeOnlineClient(client.getNickname());

		getServerDispatcher().dispatchQuery(
				new Query(QueryTypes.ClientDisconnected, client.getNickname()));
	}

}
