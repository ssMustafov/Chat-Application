package com.sirma.itt.javacourse.chatserver.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;
import com.sirma.itt.javacourse.chatcommon.utils.ServerLanguageConstants;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * @author Sinan
 */
public class Server implements Runnable {

	private static final Logger LOGGER = LogManager.getLogger(Server.class);
	private ResourceBundle bundle = LanguageBundleSingleton.getServerBundleInstance();
	private ServerSocket serverSocket;
	private ServerManager serverManager;
	private SocketsManager socketsManager;
	private View view;
	private int port;
	private boolean isRunning = false;

	public Server(View view, int port) {
		this(new ClientsManager(), new ClientsSockets(), view, port);
	}

	public Server(ServerManager serverManager, SocketsManager socketsManager, View view, int port) {
		this.serverManager = serverManager;
		this.socketsManager = socketsManager;
		this.serverManager.registerSocketsManager(this.socketsManager);
		this.view = view;
		this.port = port;
	}

	public ServerManager getServerManager() {
		return serverManager;
	}

	public SocketsManager getSocketsManager() {
		return socketsManager;
	}

	public boolean startServer() {
		try {
			serverSocket = new ServerSocket(port);
			isRunning = true;
		} catch (BindException e) {
			view.showErrorDialog(bundle.getString(ServerLanguageConstants.ANOTHER_PORT_MESSAGE));
			view.resetUI();
			LOGGER.error("Port is busy: " + port, e);
		} catch (IOException e) {
			view.showErrorDialog(bundle.getString(ServerLanguageConstants.CANNOT_START_MESSAGE));
			LOGGER.error(e.getMessage(), e);
		}
		new Thread(this).start();
		view.appendMessageToConsole(bundle
				.getString(ServerLanguageConstants.SERVER_STARTED_MESSAGE)
				+ " "
				+ serverSocket.getLocalPort());
		return isRunning;
	}

	public boolean stopServer() {
		try {
			serverSocket.close();
			serverManager.clear();
			socketsManager.clear();
			isRunning = false;
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		view.appendMessageToConsole(bundle.getString(ServerLanguageConstants.SERVER_CLOSED_MESSAGE));
		view.clearOnlineClientsList();
		LOGGER.info("Closed the server");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		acceptClients();
	}

	private void acceptClients() {
		try {
			while (isRunning) {
				Socket socket = serverSocket.accept();

				Client client = new Client();
				socketsManager.add(client.getId(), new QueryHandler(socket));
				ClientThread clientThread = new ClientThread(serverManager, socketsManager, view,
						client);
				clientThread.start();
			}
		} catch (SocketException e) {
			LOGGER.info("Server stopped accepting clients");
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
