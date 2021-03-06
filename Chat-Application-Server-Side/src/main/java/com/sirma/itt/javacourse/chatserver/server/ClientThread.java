package com.sirma.itt.javacourse.chatserver.server;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageConstants;
import com.sirma.itt.javacourse.chatcommon.utils.ServerConfig;
import com.sirma.itt.javacourse.chatserver.commands.ServerCommand;
import com.sirma.itt.javacourse.chatserver.commands.ServerCommandFactory;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Reads queries from the client. Every client has this thread working for him. Alive message is
 * sent to the client in an interval of time. The time of the alive message is set at
 * {@link ServerConfig#CLIENT_TIMEOUT}.
 * 
 * @author Sinan
 */
public class ClientThread implements Runnable {

	private static final String ALIVE_MESSAGE = "alive";
	private static final Logger LOGGER = LogManager.getLogger(ClientThread.class);
	private ResourceBundle bundle = LanguageBundleSingleton.getServerBundleInstance();
	private ServerManager serverManager;
	private SocketsManager socketsManager;
	private View view;
	private Client client;
	private QueryHandler handler;
	private Thread thisThread;

	/**
	 * Creates a new client thread with given {@link ServerManager}, {@link Client} and {@link View}
	 * of the server.
	 * 
	 * @param serverManager
	 *            - the server's manager
	 * @param socketsManager
	 *            - the sockets manager
	 * @param view
	 *            - the server's view
	 * @param client
	 *            - the connected client
	 */
	public ClientThread(ServerManager serverManager, SocketsManager socketsManager, View view,
			Client client) {
		this.serverManager = serverManager;
		this.socketsManager = socketsManager;
		this.view = view;
		this.client = client;
		handler = this.socketsManager.getHandler(this.client.getId());
	}

	/**
	 * Starts reading queries from the client in an infinite loop.
	 */
	public void start() {
		thisThread = new Thread(this);
		thisThread.start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		if (serverManager.getNumberOfOnlineClients() >= ServerConfig.CLIENT_CONNECTIONS_MAX_SIZE) {
			handler.sendQuery(new Query(QueryTypes.Refused, LanguageConstants.LOGIN_SERVER_FULL));
		} else {
			handler.sendQuery(new Query(QueryTypes.Success, ""));
			try {
				while (true) {
					try {
						Query query = handler.readQuery();
						if (query == null) {
							break;
						}
						handleClientQuery(query);
					} catch (EOFException e) {
						break;
					} catch (SocketTimeoutException e) {
						handler.sendQuery(new Query(QueryTypes.Alive, ALIVE_MESSAGE));
					}
				}
			} catch (SocketException e) {
				String formattedMessage = String.format("@%s%s", client.getNickname(),
						bundle.getString(LanguageConstants.SERVER_CLIENT_CRASHED_MESSAGE));
				view.appendMessageToConsole(formattedMessage);
				view.removeOnlineClient(client.getNickname());
				serverManager.dispatchQueryToAll(new Query(QueryTypes.ClientDisconnected, client
						.getNickname()));
				LOGGER.debug("Client lost connection: " + client.getNickname());
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}

		clearClient();
		thisThread.interrupt();
	}

	/**
	 * Removes the client from the {@link ServerManager} and closes his {@link QueryHandler}.
	 */
	private void clearClient() {
		if (client != null) {
			serverManager.removeClient(client);
			handler.closeStreams();
		}
	}

	/**
	 * Handles given {@link Query} sent from the client.
	 * 
	 * @param query
	 *            - the query sent from the client to be handled from {@link ServerCommand}
	 */
	private void handleClientQuery(Query query) {
		ServerCommand command = ServerCommandFactory.createCommand(serverManager, socketsManager,
				view, query);
		command.execute(client);
	}

}
