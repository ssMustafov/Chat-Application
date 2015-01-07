package com.sirma.itt.javacourse.chatclient.client;

import java.io.IOException;
import java.net.Socket;

import com.sirma.itt.javacourse.chatclient.utils.ServerFinder;
import com.sirma.itt.javacourse.chatclient.views.LoginForm;
import com.sirma.itt.javacourse.chatcommon.utils.Query;
import com.sirma.itt.javacourse.chatcommon.utils.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.utils.QueryTypes;

/**
 * Tries to log the client to the chat server.
 * 
 * @author smustafov
 */
public class Login extends Thread {
	private LoginForm form;
	private Socket client;
	private QueryHandler queryHandler;

	/**
	 * Create new login.
	 * 
	 * @param form
	 *            - the user interface of the login form
	 */
	public Login(LoginForm form) {
		this.form = form;
	}

	/**
	 * Connects to the chat server.
	 */
	public void connectToServer() {
		try {
			client = ServerFinder.openSocket();
			queryHandler = new QueryHandler(client);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		String nickname = form.getNickname();
		Query loginQuery = new Query(QueryTypes.Login, nickname);
		queryHandler.sendQuery(loginQuery);

		Query answer = queryHandler.readQuery();
		if (answer.getQueryType() == QueryTypes.Success) {
			form.dispose();
			Client clientThread = new Client(queryHandler, nickname);
			clientThread.startThread();
		} else {
			form.showNoticeDialog(answer.getMessage());
		}
	}

}
