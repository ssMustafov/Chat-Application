package com.sirma.itt.javacourse.chatserver.server;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.sirma.itt.javacourse.chatserver.views.View;

/**
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

	@Test
	public void testStartServer() {
		boolean isServerStarted = server.startServer();
		assertTrue(isServerStarted);
		server.stopServer();
	}

	@Test
	public void testStopServer() {
		server.startServer();
		boolean isServerStopped = server.stopServer();
		assertTrue(isServerStopped);
	}

}
