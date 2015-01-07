package com.sirma.itt.javacourse.chatcommon.utils;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Holds methods for sending and reading {@code Query}.
 * 
 * @author Sinan
 */
public class QueryHandler {
	private Socket socket;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;

	/**
	 * Creates a new {@link Query} handler with given socket.
	 * 
	 * @param socket
	 *            - the socket of the object that will use queries
	 */
	public QueryHandler(Socket socket) {
		this.socket = socket;
		createStreams();
	}

	/**
	 * Sends a {@link Query}.
	 * 
	 * @param query
	 *            - the query that will be sent
	 */
	public void sendQuery(Query query) {
		try {
			writer.writeObject(query);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads a {@link Query}.
	 * 
	 * @return - the read query
	 */
	public Query readQuery() {
		try {
			Query query = (Query) reader.readObject();
			return query;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Closes the streams of the query handler.
	 */
	public void closeStreams() {
		try {
			writer.close();
			reader.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the streams of the query handler.
	 */
	private void createStreams() {
		try {
			// The socket's output stream must be flushed first, so that it can work with
			// ObjectOutputStream. Otherwise if its not flushed sending serialized objects fails.
			OutputStream out = socket.getOutputStream();
			out.flush();

			writer = new ObjectOutputStream(out);
			reader = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
