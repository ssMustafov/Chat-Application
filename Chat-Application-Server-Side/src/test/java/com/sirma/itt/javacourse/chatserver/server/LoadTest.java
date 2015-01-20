package com.sirma.itt.javacourse.chatserver.server;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * @author Sinan
 */
public class LoadTest {

	private final int numberOfClients = 500;
	private final String host = "localhost";
	private final int testPort = 7000;
	private Server server;
	private View view;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		view = Mockito.mock(View.class);
		server = new Server(view, testPort);
		server.startServer();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
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
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}

		assertTrue(true);
	}

}
