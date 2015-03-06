package com.sirma.itt.javacourse.chatserver.server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageConstants;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Tests server's functionality.
 * 
 * @author Sinan
 */
public class FunctionalTests {

	private static final Logger LOGGER = LogManager.getLogger(FunctionalTests.class);
	private final String host = "localhost";
	private final int port = 7000;
	private Server server;
	private View view;

	/**
	 * 
	 */
	@Before
	public void setUp() {
		view = Mockito.mock(View.class);
		server = new Server(view, port);
		server.startServer();
	}

	/**
	 * 
	 */
	@After
	public void tearDown() {
		server.stopServer();
	}

	/**
	 * Tests connecting to the server and expecting successful accept.
	 */
	@Test
	public void testConnectionResponseQuerySuccess() {
		try {
			Socket socket = new Socket(host, port);
			QueryHandler clientHandler = new QueryHandler(socket);
			Query query = clientHandler.readQuery();
			Query expected = new Query(QueryTypes.Success, "");

			assertEquals(expected.toString(), query.toString());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Tests login into server with valid nickname.
	 */
	@Test
	public void testLoginWithValidNickname() {
		try {
			Socket socket = new Socket(host, port);
			QueryHandler clientHandler = new QueryHandler(socket);
			clientHandler.readQuery();
			clientHandler.sendQuery(new Query(QueryTypes.Login, "test"));

			Query expectedWelcomeQuery = new Query(QueryTypes.LoggedIn, "test");
			Query expectedNicknamesQuery = new Query(QueryTypes.ClientsNicknames, "test");
			assertEquals(expectedWelcomeQuery.toString(), clientHandler.readQuery().toString());
			clientHandler.readQuery();
			assertEquals(expectedNicknamesQuery.toString(), clientHandler.readQuery().toString());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Tests login into server with invalid nickname.
	 */
	@Test
	public void testLoginWithInvalidNickname() {
		try {
			Socket socket = new Socket(host, port);
			QueryHandler clientHandler = new QueryHandler(socket);
			clientHandler.readQuery();
			clientHandler.sendQuery(new Query(QueryTypes.Login, "[test]"));

			assertEquals(QueryTypes.Refused, clientHandler.readQuery().getQueryType());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Tests login into server with too long nickname.
	 */
	@Test
	public void testLoginWithLongNickname() {
		try {
			Socket socket = new Socket(host, port);
			QueryHandler clientHandler = new QueryHandler(socket);
			clientHandler.readQuery();
			clientHandler.sendQuery(new Query(QueryTypes.Login, "long-client-nickname_313"));

			assertEquals(QueryTypes.Refused, clientHandler.readQuery().getQueryType());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Tests login into server with empty nickname.
	 */
	@Test
	public void testLoginWithEmptyNickname() {
		try {
			Socket socket = new Socket(host, port);
			QueryHandler clientHandler = new QueryHandler(socket);
			clientHandler.readQuery();
			clientHandler.sendQuery(new Query(QueryTypes.Login, ""));

			assertEquals(QueryTypes.Refused, clientHandler.readQuery().getQueryType());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Tests logout after logging in.
	 */
	@Test
	public void testLogoutAfterLogin() {
		try {
			Socket socket = new Socket(host, port);
			QueryHandler clientHandler = new QueryHandler(socket);
			clientHandler.readQuery();
			clientHandler.sendQuery(new Query(QueryTypes.Login, "test"));
			clientHandler.sendQuery(new Query(QueryTypes.Logout, "test"));
			clientHandler.readQuery();
			clientHandler.readQuery();
			clientHandler.readQuery();

			Query expectedLogoutQuery = new Query(QueryTypes.LoggedOut,
					LanguageConstants.CLIENT_LOGOUT_MESSAGE);
			assertEquals(expectedLogoutQuery.toString(), clientHandler.readQuery().toString());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Tests logout without login.
	 */
	@Test
	public void testLogoutWithoutLogin() {
		try {
			Socket socket = new Socket(host, port);
			QueryHandler clientHandler = new QueryHandler(socket);
			clientHandler.readQuery();
			clientHandler.sendQuery(new Query(QueryTypes.Logout, "test"));

			Query expectedLogoutQuery = new Query(QueryTypes.Refused,
					LanguageConstants.CLIENT_NOT_LOGGED_IN_MESSAGE);
			assertEquals(expectedLogoutQuery.toString(), clientHandler.readQuery().toString());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Tests to send a message with two clients.
	 */
	@Test
	public void testSendMessageWithTwoClients() {
		try {
			Socket socket1 = new Socket(host, port);
			Socket socket2 = new Socket(host, port);
			QueryHandler clientHandler1 = new QueryHandler(socket1);
			clientHandler1.readQuery();
			clientHandler1.sendQuery(new Query(QueryTypes.Login, "pesho"));
			clientHandler1.readQuery();
			clientHandler1.readQuery();
			clientHandler1.readQuery();

			QueryHandler clientHandler2 = new QueryHandler(socket2);
			clientHandler2.readQuery();
			clientHandler2.sendQuery(new Query(QueryTypes.Login, "gosho"));
			clientHandler2.readQuery();
			clientHandler2.readQuery();
			clientHandler2.readQuery();

			clientHandler1.readQuery();
			clientHandler1.sendQuery(new Query(QueryTypes.SendMessage, "hello!"));

			Query expectedQuery = new Query(QueryTypes.SentMessage, "<pesho>: Hello!");
			assertEquals(expectedQuery.toString(), clientHandler1.readQuery().toString());
			assertEquals(expectedQuery.toString(), clientHandler2.readQuery().toString());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Tests notification when a client is connected. Tests with two connected clients.
	 */
	@Test
	public void testClientConnectedNotificationWithTwoClients() {
		try {
			Socket socket1 = new Socket(host, port);
			Socket socket2 = new Socket(host, port);
			QueryHandler clientHandler1 = new QueryHandler(socket1);
			clientHandler1.readQuery();
			clientHandler1.sendQuery(new Query(QueryTypes.Login, "pesho"));
			clientHandler1.readQuery();
			clientHandler1.readQuery();
			clientHandler1.readQuery();

			QueryHandler clientHandler2 = new QueryHandler(socket2);
			clientHandler2.readQuery();
			clientHandler2.sendQuery(new Query(QueryTypes.Login, "gosho"));
			clientHandler2.readQuery();
			clientHandler2.readQuery();

			Query expectedQuery = new Query(QueryTypes.ClientConnected, "gosho");
			assertEquals(expectedQuery.toString(), clientHandler1.readQuery().toString());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Tests client disconnected notification with two clients.
	 */
	@Test
	public void testClientDisconnectedNotificationWithTwoClients() {
		try {
			Socket socket1 = new Socket(host, port);
			Socket socket2 = new Socket(host, port);
			QueryHandler clientHandler1 = new QueryHandler(socket1);
			clientHandler1.readQuery();
			clientHandler1.sendQuery(new Query(QueryTypes.Login, "pesho"));
			clientHandler1.readQuery();
			clientHandler1.readQuery();

			QueryHandler clientHandler2 = new QueryHandler(socket2);
			clientHandler2.readQuery();
			clientHandler2.sendQuery(new Query(QueryTypes.Login, "gosho"));
			clientHandler2.readQuery();
			clientHandler2.readQuery();
			clientHandler2.readQuery();

			clientHandler1.sendQuery(new Query(QueryTypes.Logout, "pesho"));

			Query expectedQuery = new Query(QueryTypes.ClientDisconnected, "pesho");
			assertEquals(expectedQuery.toString(), clientHandler2.readQuery().toString());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Tests notification when a client is connected. Tests with three connected clients.
	 */
	@Test
	public void testClientConnectedNotificationWithThreeClients() {
		try {
			Socket socket1 = new Socket(host, port);
			Socket socket2 = new Socket(host, port);
			Socket socket3 = new Socket(host, port);
			QueryHandler clientHandler1 = new QueryHandler(socket1);
			clientHandler1.readQuery();
			clientHandler1.sendQuery(new Query(QueryTypes.Login, "pesho"));
			clientHandler1.readQuery();
			clientHandler1.readQuery();
			clientHandler1.readQuery();

			QueryHandler clientHandler2 = new QueryHandler(socket2);
			clientHandler2.readQuery();
			clientHandler2.sendQuery(new Query(QueryTypes.Login, "gosho"));
			clientHandler2.readQuery();
			clientHandler2.readQuery();
			clientHandler2.readQuery();

			QueryHandler clientHandler3 = new QueryHandler(socket3);
			clientHandler3.readQuery();
			clientHandler3.sendQuery(new Query(QueryTypes.Login, "test-client"));
			clientHandler3.readQuery();
			clientHandler3.readQuery();
			clientHandler3.readQuery();

			Query expectedQuery1 = new Query(QueryTypes.ClientConnected, "gosho");
			Query expectedQuery2 = new Query(QueryTypes.ClientConnected, "test-client");

			assertEquals(expectedQuery1.toString(), clientHandler1.readQuery().toString());
			assertEquals(expectedQuery2.toString(), clientHandler1.readQuery().toString());
			assertEquals(expectedQuery2.toString(), clientHandler2.readQuery().toString());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
