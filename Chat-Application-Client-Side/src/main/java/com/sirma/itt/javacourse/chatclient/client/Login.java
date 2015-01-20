package com.sirma.itt.javacourse.chatclient.client;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

import com.sirma.itt.javacourse.chatclient.utils.ServerFinder;
import com.sirma.itt.javacourse.chatclient.views.LoginForm;
import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;

/**
 * @author Sinan
 */
public class Login implements Runnable {

	private ServerFinder serverFinder;
	private Socket socket;
	private QueryHandler queryHandler;
	private LoginForm form;

	public Login(LoginForm form) {
		this.form = form;
	}

	public void connectToServer() {
		serverFinder = new ServerFinder(form.getProgressBar());
		serverFinder.execute();
		new Thread(this).start();
	}

	private void setSocket() {
		try {
			socket = serverFinder.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		setSocket();
		if (socket == null) {
			form.showErrorDialog("No running server");
		} else {
			queryHandler = new QueryHandler(socket);

			String nickname = form.getNickname();
			Query loginQuery = new Query(QueryTypes.Login, nickname);
			queryHandler.sendQuery(loginQuery);

			try {
				Query answer = queryHandler.readQuery();
				if (answer.getQueryType() == QueryTypes.LoggedIn) {
					form.dispose();
					Client client = new Client(queryHandler, nickname);
					client.startThread();
				} else {
					form.showNoticeDialog(answer.getMessage());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
