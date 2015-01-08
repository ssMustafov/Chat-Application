package com.sirma.itt.javacourse.chatserver.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Represents the server for the Chat application. Implements {@link Runnable}. The server can be
 * started via startServer method. When is started the server starts to listen at the given port.
 * Accepts clients as for every client starts two new threads - {@link ClientReader} for handling
 * the reading from the client and {@link ClientWriter} for handling the writing to the client.
 * 
 * @see Server#startServer()
 * @author Sinan
 */
public class Server implements Runnable {
	public static final int CLIENT_READ_TIMEOUT = 300000;
	private static final Logger LOGGER = LogManager.getLogger(Server.class);
	private ServerSocket serverSocket;
	private ServerDispatcher serverDispatcher = new ServerDispatcher();
	private View view;
	private ResourceBundle bundle = LanguageBundleSingleton.getServerBundleInstance();
	private boolean isClosed = false;
	private int port;

	/**
	 * Creates a new chat server with given port.
	 * 
	 * @param port
	 *            - the port in which the server will listen for clients
	 * @param view
	 *            - the view of the server
	 */
	public Server(View view, int port) {
		this.view = view;
		this.port = port;
		serverDispatcher.start();
	}

	/**
	 * Starts the server.
	 */
	public void startServer() {
		new Thread(this).start();
	}

	/**
	 * Stops the server.
	 */
	public synchronized void stopServer() {
		isClosed = true;
		serverDispatcher.clear();
		try {
			serverSocket.close();
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			LOGGER.error("Cannot close the server", e);
		}
		view.appendMessageToConsole(bundle.getString("closed"));
		view.clearOnlineClientsList();
		LOGGER.info("Closed the server");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		createServer();
		acceptClients();
	}

	/**
	 * Initializes the server socket.
	 */
	private void createServer() {
		try {
			serverSocket = new ServerSocket(port);
			view.appendMessageToConsole(bundle.getString("started") + " " + port);
		} catch (BindException e) {
			view.showErrorDialog(bundle.getString("anotherPort"));
			view.resetUI();
			LOGGER.error("Port is busy: " + port, e);
		} catch (IOException e) {
			view.showErrorDialog(bundle.getString("cannotStart"));
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Starts to accept clients in a loop until the server is not stopped.
	 */
	private void acceptClients() {
		try {
			while (!isClosed) {
				Socket clientSocket = serverSocket.accept();
				handleClient(clientSocket);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				LOGGER.error("Cannot close the server", e);
			}
		}
	}

	/**
	 * Handles a client by adding him to the {@link ServerDispatcher} and creating the two threads
	 * for reading and writing - {@link ClientReaderThread} and {@link ClientWriterThread}.
	 * 
	 * @param clientSocket
	 *            - the socket of the client
	 */
	private void handleClient(Socket clientSocket) {
		Client client = new Client(clientSocket);

		ClientReaderThread clientReader = new ClientReaderThread(serverDispatcher, client, view);
		// ClientWriterThread clientWriter = new ClientWriterThread(serverDispatcher, client);
		clientReader.start();
		// clientWriter.start();
	}

}
