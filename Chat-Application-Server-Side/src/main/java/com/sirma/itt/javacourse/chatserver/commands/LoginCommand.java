package com.sirma.itt.javacourse.chatserver.commands;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageConstants;
import com.sirma.itt.javacourse.chatcommon.utils.Validator;
import com.sirma.itt.javacourse.chatserver.server.Client;
import com.sirma.itt.javacourse.chatserver.server.ServerManager;
import com.sirma.itt.javacourse.chatserver.server.SocketsManager;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Handles {@link QueryTypes#Login} query.
 * 
 * @author Sinan
 */
public class LoginCommand extends ServerCommand {

	private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
	private Query query;
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
	 * @param query
	 *            - the client's query
	 */
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
			handler.sendQuery(new Query(QueryTypes.Refused,
					LanguageConstants.LOGIN_TAKEN_NICKNAME_MESSAGE));
			return;
		}
		if (nickname.isEmpty()) {
			handler.sendQuery(new Query(QueryTypes.Refused,
					LanguageConstants.LOGIN_EMPTY_NICKNAME_MESSAGE));
			return;
		}
		if (nickname.length() > Validator.MAX_NICKNAME_LENGHT) {
			handler.sendQuery(new Query(QueryTypes.Refused,
					LanguageConstants.LOGIN_MAX_ALLOWED_NICKNAME_LENGTH_MESSAGE));
			return;
		}
		if (!Validator.isValidNickname(nickname)) {
			handler.sendQuery(new Query(QueryTypes.Refused,
					LanguageConstants.LOGIN_INVALID_NICKNAME_MESSAGE));
			return;
		}

		client.setNickname(nickname);
		getServerManager().dispatchQueryToAll(new Query(QueryTypes.ClientConnected, nickname));

		sleep(100);

		getServerManager().addClient(client);

		getView().addOnlineClient(nickname);
		getView().appendMessageToConsole(
				bundle.getString(LanguageConstants.SERVER_CLIENT_CONNECTED_MESSAGE) + nickname);
		getView().appendMessageToConsole(
				bundle.getString(LanguageConstants.SERVER_THREAD_STARTED_MESSAGE) + nickname);

		String onlineClientsNicknames = getServerManager().getOnlineClientsNicknames();
		handler.sendQuery(new Query(QueryTypes.LoggedIn, nickname));
		handler.sendQuery(new Query(QueryTypes.LoggedIn, LanguageConstants.CLIENT_WELCOME_MESSAGE));
		handler.sendQuery(new Query(QueryTypes.ClientsNicknames, onlineClientsNicknames));
	}

	/**
	 * Sleeps for the given time.
	 * 
	 * @param time
	 *            - the time to sleep in ms
	 */
	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
