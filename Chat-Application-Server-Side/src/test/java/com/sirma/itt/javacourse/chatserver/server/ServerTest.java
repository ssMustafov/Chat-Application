package com.sirma.itt.javacourse.chatserver.server;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Tests the {@link Server} class.
 * 
 * @author Sinan
 */
public class ServerTest {

	private View view;
	private Server server;

	/**
	 *
	 */
	@Before
	public void setUp() {
		view = Mockito.mock(View.class);
		server = new Server(view, 7000);
	}

	/**
	 * Tests {@link Server#startServer()} to check if its returning the right value.
	 */
	@Test
	public void testStartServer() {
		boolean isServerStarted = server.startServer();
		assertTrue(isServerStarted);
		server.stopServer();
	}

	/**
	 * Tests {@link Server#stopServer()} to check if its returning the right value.
	 */
	@Test
	public void testStopServer() {
		server.startServer();
		boolean isServerStopped = server.stopServer();
		assertTrue(isServerStopped);
	}

}
