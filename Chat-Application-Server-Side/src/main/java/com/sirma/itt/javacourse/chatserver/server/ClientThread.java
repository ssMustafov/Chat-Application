package com.sirma.itt.javacourse.chatserver.server;

import java.io.IOException;
import java.net.SocketTimeoutException;

import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;
import com.sirma.itt.javacourse.chatserver.commands.ServerCommand;
import com.sirma.itt.javacourse.chatserver.commands.ServerCommandFactory;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * @author Sinan
 */
public class ClientThread implements Runnable {

	private ServerManager serverManager;
	private SocketsManager socketsManager;
	private View view;
	private Client client;
	private QueryHandler handler;

	public ClientThread(ServerManager serverManager, SocketsManager socketsManager, View view,
			Client client) {
		this.serverManager = serverManager;
		this.socketsManager = socketsManager;
		this.view = view;
		this.client = client;
		handler = this.socketsManager.getHandler(this.client.getId());
	}

	public void start() {
		new Thread(this).start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		try {
			while (true) {
				try {
					Query query = handler.readQuery();
					if (query == null) {
						break;
					}
					handleClientQuery(query);
				} catch (SocketTimeoutException e) {
					handler.sendQuery(new Query(QueryTypes.Alive, "!alive"));
				}
			}
		} catch (IOException e) {
			clearClient();
			String formattedMessage = String.format("@%s's connection crashed.",
					client.getNickname());
			view.appendMessageToConsole(formattedMessage);
			view.removeOnlineClient(client.getNickname());
			serverManager.dispatchQueryToAll(new Query(QueryTypes.ClientDisconnected, client
					.getNickname()));
			return;
		}

		clearClient();
	}

	private void clearClient() {
		if (client != null) {
			serverManager.removeClient(client);
			handler.closeStreams();
		}
	}

	private void handleClientQuery(Query query) {
		ServerCommand command = ServerCommandFactory.createCommand(serverManager, socketsManager,
				view, query);
		command.execute(client);
	}

}
