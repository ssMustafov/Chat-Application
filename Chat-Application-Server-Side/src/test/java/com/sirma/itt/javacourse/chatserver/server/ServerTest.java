package com.sirma.itt.javacourse.chatserver.server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sirma.itt.javacourse.chatcommon.utils.Query;
import com.sirma.itt.javacourse.chatcommon.utils.QueryHandler;
import com.sirma.itt.javacourse.chatcommon.utils.QueryTypes;
import com.sirma.itt.javacourse.chatserver.views.MockView;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Tests {@link com.sirma.itt.javacourse.chatserver.server.Server} class.
 * 
 * @author smustafov
 */
public class ServerTest {

	private static final Logger LOGGER = LogManager.getLogger(ServerTest.class);
	private final String host = "localhost";
	private final int testPort = 7000;
	private Server server;
	private View view = new MockView();

	private Socket testClient;

	/**
	 * 
	 */
	@Before
	public void setUp() {
		server = new Server(view, testPort);
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
	 * Tests login into server with valid client.
	 */
	@Test
	public void testServerLoginWithValidClient() {
		try {
			testClient = new Socket(host, testPort);
		} catch (UnknownHostException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error("Closed", e);
		}

		QueryHandler queryHandler = new QueryHandler(testClient);
		queryHandler.sendQuery(new Query(QueryTypes.Login, "Test"));
		Query answer = queryHandler.readQuery();
		queryHandler.sendQuery(new Query(QueryTypes.Logout, "Test"));

		assertEquals(QueryTypes.Success, answer.getQueryType());
	}

	/**
	 * Tests login into server with invalid client.
	 */
	@Test
	public void testServerLoginInvalidClient() {
		try {
			testClient = new Socket(host, testPort);
		} catch (UnknownHostException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error("Closed", e);
		}

		QueryHandler queryHandler = new QueryHandler(testClient);
		queryHandler.sendQuery(new Query(QueryTypes.Login, "clie[]nt"));
		Query answer = queryHandler.readQuery();

		assertEquals(QueryTypes.Refused, answer.getQueryType());
	}

	/**
	 * Tests client logout.
	 */
	@Test
	public void testServerLogout() {
		try {
			testClient = new Socket(host, testPort);
		} catch (UnknownHostException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error("Closed", e);
		}

		QueryHandler queryHandler = new QueryHandler(testClient);
		queryHandler.sendQuery(new Query(QueryTypes.Login, "Client"));
		queryHandler.readQuery();
		queryHandler.readQuery();
		queryHandler.readQuery();

		queryHandler.sendQuery(new Query(QueryTypes.Logout, "Client"));
		Query answer = queryHandler.readQuery();

		assertEquals(QueryTypes.Success, answer.getQueryType());
	}

}
