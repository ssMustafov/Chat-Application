package com.sirma.itt.javacourse.chatserver.server;

import java.net.Socket;

import com.sirma.itt.javacourse.chatcommon.utils.Query;
import com.sirma.itt.javacourse.chatcommon.utils.QueryHandler;

/**
 * Represents a client in the server.
 * 
 * @author Sinan
 */
public class Client {
	private QueryHandler queryHandler;
	private String nickname = "";

	/**
	 * Creates a new client with given socket.
	 * 
	 * @param clientSocket
	 *            - the socket of the client
	 */
	public Client(Socket clientSocket) {
		queryHandler = new QueryHandler(clientSocket);
	}

	/**
	 * Sets the nickname of the client.
	 * 
	 * @param nickname
	 *            - the nickname to be set on the client
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Returns the nickname of the client.
	 * 
	 * @return - the nickname of the client
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Sends a {@link Query} to the client.
	 * 
	 * @param query
	 *            - the query that will be sent to the client
	 */
	public void sendQuery(Query query) {
		queryHandler.sendQuery(query);
	}

	/**
	 * Reads a {@link Query} from the client.
	 * 
	 * @return - the read query from the client
	 */
	public Query readQuery() {
		return queryHandler.readQuery();
	}

	/**
	 * Closes the clients input and output streams.
	 */
	public void closeClient() {
		queryHandler.closeStreams();
	}

}
