package com.sirma.itt.javacourse.chatcommon.models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import com.sirma.itt.javacourse.chatcommon.utils.ServerConfig;

/**
 * @author Sinan
 */
public class QueryHandler {
	private Socket socket;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;

	public QueryHandler(Socket socket) {
		this.socket = socket;
		createStreams();
		setClientTimeout();
	}

	public void sendQuery(Query query) {
		try {
			objectOutputStream.writeObject(query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Query readQuery() throws IOException {
		Query query = null;

		try {
			query = (Query) objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return query;
	}

	public void closeStreams() {
		try {
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createStreams() {
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setClientTimeout() {
		try {
			this.socket.setSoTimeout(ServerConfig.CLIENT_TIMEOUT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}
