package com.sirma.itt.javacourse.chatserver.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageConstants;
import com.sirma.itt.javacourse.chatcommon.utils.ServerConfig;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Represents the server for the Chat application. Implements {@link Runnable}. The server can be
 * started via startServer method. When is started the server starts to listen at the given port.
 * Accepts clients as for every client starts a new thread - {@link ClientThread} for handling the
 * reading from the client.
 * 
 * @see Server#startServer()
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
	private ExecutorService threadPool = Executors
			.newFixedThreadPool(ServerConfig.THREAD_POOL_MAX_SIZE);

	/**
	 * Creates a new chat server with given {@link View} and port.
	 * 
	 * @param port
	 *            - the port in which the server will listen for clients
	 * @param view
	 *            - the view of the server
	 */
	public Server(View view, int port) {
		this(new ClientsManager(), new ClientsSockets(), view, port);
	}

	/**
	 * Creates a new chat server with given {@link ServerManager}, {@link SocketsManager},
	 * {@link View} and port.
	 * 
	 * @param serverManager
	 *            - the server manager
	 * @param socketsManager
	 *            - the sockets manager
	 * @param view
	 *            - the view of the server
	 * @param port
	 *            - the port in which the server will listen for clients
	 */
	public Server(ServerManager serverManager, SocketsManager socketsManager, View view, int port) {
		this.serverManager = serverManager;
		this.socketsManager = socketsManager;
		this.serverManager.registerSocketsManager(this.socketsManager);
		this.view = view;
		this.port = port;
	}

	/**
	 * Returns the current {@link ServerManager}.
	 * 
	 * @return - the current server manager
	 */
	public ServerManager getServerManager() {
		return serverManager;
	}

	/**
	 * Returns the current {@link SocketsManager} sockets manager.
	 * 
	 * @return - the current sockets manager
	 */
	public SocketsManager getSocketsManager() {
		return socketsManager;
	}

	/**
	 * Starts the server.
	 * 
	 * @return - true if the server is started; otherwise false
	 */
	public boolean startServer() {
		try {
			serverSocket = new ServerSocket(port);
			isRunning = true;
		} catch (BindException e) {
			view.showErrorDialog(bundle.getString(LanguageConstants.SERVER_ANOTHER_PORT_MESSAGE));
			view.resetUI();
			LOGGER.error("Port is busy: " + port, e);
		} catch (IOException e) {
			view.showErrorDialog(bundle.getString(LanguageConstants.SERVER_CANNOT_START_MESSAGE));
			LOGGER.error(e.getMessage(), e);
		}
		new Thread(this).start();
		view.appendMessageToConsole(bundle.getString(LanguageConstants.SERVER_STARTED_MESSAGE)
				+ " " + serverSocket.getLocalPort());
		return isRunning;
	}

	/**
	 * Stops the server.
	 * 
	 * @return - true if the server is stopped; otherwise false
	 */
	public boolean stopServer() {
		try {
			serverManager.dispatchQueryToAll(new Query(QueryTypes.Closed,
					LanguageConstants.SERVER_CLOSED_MESSAGE));
			serverSocket.close();
			serverManager.clear();
			socketsManager.clear();
			isRunning = false;
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		view.appendMessageToConsole(bundle.getString(LanguageConstants.SERVER_CLOSED_MESSAGE));
		view.clearOnlineClientsList();
		threadPool.shutdown();
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

	/**
	 * Starts to accept clients in a loop until the server is not stopped.
	 */
	private void acceptClients() {
		try {
			while (isRunning) {
				Socket socket = serverSocket.accept();

				Client client = new Client();
				socketsManager.add(client.getId(), new QueryHandler(socket));
				ClientThread clientThread = new ClientThread(serverManager, socketsManager, view,
						client);
				threadPool.execute(clientThread);
			}
		} catch (SocketException e) {
			LOGGER.info("Server stopped accepting clients");
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
