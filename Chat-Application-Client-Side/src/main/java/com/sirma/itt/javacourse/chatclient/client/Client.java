package com.sirma.itt.javacourse.chatclient.client;

import com.sirma.itt.javacourse.chatclient.views.ClientView;
import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;

/**
 * @author Sinan
 */
public class Client {

	private ClientThread thread;
	private QueryHandler queryHandler;
	private View view;
	private String nickname;

	/**
	 * @param queryHandler
	 * @param nickname
	 */
	public Client(QueryHandler queryHandler, String nickname) {
		this.queryHandler = queryHandler;
		this.nickname = nickname;
		this.view = new ClientView(this);
	}

	/**
	 * Starts the client's thread.
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
	 * Sends a {@link Query} to logout this client.
	 */
	public void logout() {
		queryHandler.sendQuery(new Query(QueryTypes.Logout, nickname));
		thread.stop();
	}
}
