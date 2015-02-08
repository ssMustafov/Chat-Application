package com.sirma.itt.javacourse.chatcommon.models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sirma.itt.javacourse.chatcommon.utils.ServerConfig;

/**
 * Holds client {@link Socket} and streams for writing and reading. This class must be used for
 * sending and reading {@link Query}ies. A client's socket must be closed from this class. It uses
 * {@link ObjectInputStream} and {@link ObjectOutputStream} as streams.
 * 
 * @author Sinan
 */
public class QueryHandler {

	private static final Logger LOGGER = LogManager.getLogger(QueryHandler.class);
	private Socket socket;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;

	/**
	 * Creates a new query handler with given client socket.
	 * 
	 * @param socket
	 *            - the socket of the client
	 */
	public QueryHandler(Socket socket) {
		this.socket = socket;
		createStreams();
		setClientTimeout();
	}

	/**
	 * Sends given {@link Query}.
	 * 
	 * @param query
	 *            - the query to be sent through the socket
	 */
	public void sendQuery(Query query) {
		try {
			objectOutputStream.writeObject(query);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Reads and returns a read {@link Query} from the socket.
	 * 
	 * @return - the read query from the socket
	 * @throws IOException
	 *             - thrown when I/O error occurs
	 */
	public Query readQuery() throws IOException {
		Query query = null;

		try {
			query = (Query) objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}

		return query;
	}

	/**
	 * Closes the streams of this socket and the socket.
	 */
	public void closeStreams() {
		try {
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Creates the socket's streams.
	 */
	private void createStreams() {
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Sets timeout for reading.
	 */
	private void setClientTimeout() {
		try {
			this.socket.setSoTimeout(ServerConfig.CLIENT_TIMEOUT);
		} catch (SocketException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
