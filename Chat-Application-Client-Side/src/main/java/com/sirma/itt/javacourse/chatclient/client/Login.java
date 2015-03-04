package com.sirma.itt.javacourse.chatclient.client;

import java.io.IOException;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sirma.itt.javacourse.chatclient.utils.ServerFinder;
import com.sirma.itt.javacourse.chatclient.views.LoginForm;
import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageConstants;
import com.sirma.itt.javacourse.chatcommon.utils.Validator;

/**
 * Represents the {@link LoginForm}'s logic. It searches for the server's port and tries to login.
 * If the server responds with successful login a new client view is created and the login form is
 * disposed.
 * 
 * @author Sinan
 */
public class Login implements Runnable {

	private static final Logger LOGGER = LogManager.getLogger(Login.class);
	private ResourceBundle bundle = LanguageBundleSingleton.getClientLoginBundleInstance();
	private ServerFinder serverFinder;
	private Socket socket;
	private QueryHandler queryHandler;
	private LoginForm form;

	/**
	 * Creates a new login with given {@link LoginForm}.
	 * 
	 * @param form
	 *            - the login form of the client
	 */
	public Login(LoginForm form) {
		this.form = form;
	}

	/**
	 * Starts to search for the server and connects to it, then starts this thread and sends login
	 * query to the server.
	 */
	public void connectToServer() {
		serverFinder = new ServerFinder(form.getProgressBar());
		serverFinder.execute();
		new Thread(this).start();
	}

	/**
	 * Sets the client socket. It gets it from the {@link ServerFinder} thread. This method blocks
	 * until the server is found.
	 */
	private void setSocket() {
		try {
			socket = serverFinder.get();
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (ExecutionException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		setSocket();
		if (socket == null) {
			form.showErrorDialog(bundle.getString(LanguageConstants.LOGIN_NO_SERVER_MESSAGE));
		} else {
			queryHandler = new QueryHandler(socket);
			try {
				Query canProceedQuery = queryHandler.readQuery();
				if (canProceedQuery.getQueryType() == QueryTypes.Success) {
					proccesLogin();
				} else {
					form.showNoticeDialog(bundle.getString(canProceedQuery.getMessage()));
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Procceses login to the server. If server accepts the client's nickname creates new
	 * {@link View} for the chat and closes the login form.
	 */
	private void proccesLogin() {
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
				String bundleKey = answer.getMessage();
				String errorMessage = "";
				if (LanguageConstants.LOGIN_MAX_ALLOWED_NICKNAME_LENGTH_MESSAGE.equals(bundleKey)) {
					errorMessage = bundle.getString(bundleKey) + Validator.MAX_NICKNAME_LENGHT;
				} else {
					errorMessage = bundle.getString(bundleKey);
				}
				form.showNoticeDialog(errorMessage);
				socket.close();
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
