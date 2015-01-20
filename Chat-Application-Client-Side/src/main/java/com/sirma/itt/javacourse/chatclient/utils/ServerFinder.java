package com.sirma.itt.javacourse.chatclient.utils;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sirma.itt.javacourse.chatcommon.utils.ServerConfig;

/**
 * Searches for the server at defined ports range in {@link ServerConfig}. If the server is found it
 * returns a client socket via the method {@link SwingWorker#get()}.
 * 
 * @author Sinan
 */
public final class ServerFinder extends SwingWorker<Socket, Void> {
	private static final Logger LOGGER = LogManager.getLogger(ServerFinder.class);
	private static final int MIN_PORT = Integer.parseInt(ServerConfig.SERVER_PORTS[0]);
	private static final int MAX_PORT = Integer
			.parseInt(ServerConfig.SERVER_PORTS[ServerConfig.SERVER_PORTS.length - 1]);

	private JProgressBar progressBar;

	/**
	 * Creates a new server finder with given progress bar.
	 * 
	 * @param progressBar
	 *            - the progress bar of the login form
	 */
	public ServerFinder(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Socket doInBackground() throws Exception {
		progressBar.setVisible(true);

		for (int port = MIN_PORT; port <= MAX_PORT; port++) {
			try {
				return new Socket(ServerConfig.HOST, port);
			} catch (IOException e) {
				LOGGER.info("Searching server in port: " + port);
			}
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void done() {
		progressBar.setVisible(false);
	}
}
