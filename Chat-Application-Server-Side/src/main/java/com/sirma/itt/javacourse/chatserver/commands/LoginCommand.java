package com.sirma.itt.javacourse.chatserver.commands;

import java.util.ResourceBundle;

import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;
import com.sirma.itt.javacourse.chatcommon.utils.ServerLanguageConstants;
import com.sirma.itt.javacourse.chatcommon.utils.Validator;
import com.sirma.itt.javacourse.chatserver.server.Client;
import com.sirma.itt.javacourse.chatserver.server.ServerManager;
import com.sirma.itt.javacourse.chatserver.server.SocketsManager;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * @author Sinan
 */
public class LoginCommand extends ServerCommand {

	private Query query;
	private ResourceBundle bundle = LanguageBundleSingleton.getServerBundleInstance();

	public LoginCommand(ServerManager serverManager, SocketsManager socketsManager, View view,
			Query query) {
		super(serverManager, socketsManager, view);
		this.query = query;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Client client) {
		QueryHandler handler = getSocketsManager().getHandler(client.getId());
		String nickname = query.getMessage();

		if (getServerManager().containsClient(nickname)) {
			handler.sendQuery(new Query(QueryTypes.Refused, "That nickname is already in use"));
			return;
		}
		if (nickname.isEmpty()) {
			handler.sendQuery(new Query(QueryTypes.Refused, "Nickname is empty"));
			return;
		}
		if (nickname.length() > Validator.MAX_NICKNAME_LENGHT) {
			handler.sendQuery(new Query(QueryTypes.Refused,
					"The maximum length of the nickname is " + Validator.MAX_NICKNAME_LENGHT));
			return;
		}
		if (!Validator.isValidNickname(nickname)) {
			handler.sendQuery(new Query(QueryTypes.Refused,
					"The nickname contains forbidden symbols. Can consist of letters, numbers, dash and underscore"));
			return;
		}

		client.setNickname(nickname);
		getServerManager().dispatchQueryToAll(new Query(QueryTypes.ClientConnected, nickname));

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		getServerManager().addClient(client);

		getView().addOnlineClient(nickname);
		getView().appendMessageToConsole(
				bundle.getString(ServerLanguageConstants.CLIENT_CONNECTED_MESSAGE) + nickname);
		getView().appendMessageToConsole(
				bundle.getString(ServerLanguageConstants.THREAD_STARTED_MESSAGE) + nickname);

		String onlineClientsNicknames = getServerManager().getOnlineClientsNicknames();
		handler.sendQuery(new Query(QueryTypes.LoggedIn, "Welcome, " + nickname));
		handler.sendQuery(new Query(QueryTypes.ClientsNicknames, onlineClientsNicknames));
	}

}
