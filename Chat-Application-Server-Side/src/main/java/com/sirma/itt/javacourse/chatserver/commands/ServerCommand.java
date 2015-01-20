package com.sirma.itt.javacourse.chatserver.commands;

import com.sirma.itt.javacourse.chatserver.server.Client;
import com.sirma.itt.javacourse.chatserver.server.ServerManager;
import com.sirma.itt.javacourse.chatserver.server.SocketsManager;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * @author Sinan
 */
public abstract class ServerCommand {
	private View view;
	private ServerManager serverManager;
	private SocketsManager socketsManager;

	public ServerCommand(ServerManager serverManager, SocketsManager socketsManager, View view) {
		this.serverManager = serverManager;
		this.socketsManager = socketsManager;
		this.view = view;
	}

	protected View getView() {
		return view;
	}

	protected ServerManager getServerManager() {
		return serverManager;
	}

	protected SocketsManager getSocketsManager() {
		return socketsManager;
	}

	public abstract void execute(Client client);

}
