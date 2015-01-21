package com.sirma.itt.javacourse.chatserver.server;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Tests the server by connecting alot of clients to it.
 * 
 * @author Sinan
 */
public class LoadTest {

	private static final Logger LOGGER = LogManager.getLogger(LoadTest.class);
	private final int numberOfClients = 500;
	private final String host = "localhost";
	private final int testPort = 7000;
	private Server server;
	private View view;

	/**
	 * 
	 */
	@Before
	public void setUp() {
		view = Mockito.mock(View.class);
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
				new Socket(host, testPort);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

		assertTrue(true);
	}

}
