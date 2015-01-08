package com.sirma.itt.javacourse.chatclient.client;

import com.sirma.itt.javacourse.chatclient.views.ClientView;
import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.utils.Query;
import com.sirma.itt.javacourse.chatcommon.utils.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.utils.QueryTypes;

/**
 * Holds methods for connecting a client to the server.
 * 
 * @author Sinan
 */
public class Client {

	private QueryHandler queryHandler;
	private View view;
	private String nickname;
	private ClientThread thread;

	/**
	 * @param handler
	 * @param nickname
	 */
	public Client(QueryHandler handler, String nickname) {
		this.queryHandler = handler;
		this.nickname = nickname;
		this.view = new ClientView(this);
	}

	/**
	 * Connects to the chat server.
	 */
	public void startThread() {
		thread = new ClientThread(queryHandler, view);
		thread.start();
	}

	/**
	 * Sets the client's nickname.
	 * 
	 * @param nickname
	 *            - the nickname to be set
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
	 * Sends a message to the all clients of the chat application.
	 * 
	 * @param message
	 *            - the message to be send
	 */
	public void sendMessage(String message) {
		queryHandler.sendQuery(new Query(QueryTypes.SendMessage, message));
	}

	/**
	 * Logges out the client.
	 */
	public void logout() {
		queryHandler.sendQuery(new Query(QueryTypes.Logout, nickname));
		thread.interrupt();
		queryHandler.closeStreams();
	}

}
