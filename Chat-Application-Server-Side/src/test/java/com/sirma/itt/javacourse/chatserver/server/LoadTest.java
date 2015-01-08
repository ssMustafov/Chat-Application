package com.sirma.itt.javacourse.chatserver.server;

import static org.junit.Assert.assertTrue;

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
 * Tests the chat application for connecting a lot of clients.
 * 
 * @author smustafov
 */
public class LoadTest {

	private static final Logger LOGGER = LogManager.getLogger(ServerTest.class);
	private final int numberOfClients = 500;
	private final String host = "localhost";
	private final int testPort = 7000;
	private Server server;
	private View view = new MockView();

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
	 * Tests the chat {@link Server} by connecting {@link LoadTest#numberOfClients} clients.
	 */
	@Test
	public void testConnectingAlotOfClients() {
		try {
			for (int i = 1; i <= numberOfClients; i++) {
				Socket client = new Socket(host, testPort);
				QueryHandler queryHandler = new QueryHandler(client);
				queryHandler.sendQuery(new Query(QueryTypes.Login, "client-" + i));
			}
		} catch (UnknownHostException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error("Closed", e);
		}

		assertTrue(true);
	}

}
