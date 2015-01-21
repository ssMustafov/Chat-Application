package com.sirma.itt.javacourse.chatclient.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;

/**
 * @author Sinan
 */
public class ClientCommandFactoryTest {

	private View view;

	/**
	 * 
	 */
	@Before
	public void setUp() {
		view = Mockito.mock(View.class);
	}

	/**
	 * Tests {@link ClientCommandFactory#createCommand(View, Query)} by creating query with type
	 * LoggedIn in.
	 */
	@Test
	public void testCreateLoggedInCommand() {
		ClientCommand command = ClientCommandFactory.createCommand(view, new Query(
				QueryTypes.LoggedIn, "logged"));

		assertTrue(command instanceof LoggedInCommand);
	}

	/**
	 * Tests {@link ClientCommandFactory#createCommand(View, Query)} by creating query with type
	 * ClientsNicknames.
	 */
	@Test
	public void testCreateClientsNicknamesCommand() {
		ClientCommand command = ClientCommandFactory.createCommand(view, new Query(
				QueryTypes.ClientsNicknames, "test client"));

		assertTrue(command instanceof ClientsNicknames);
	}

	/**
	 * Tests {@link ClientCommandFactory#createCommand(View, Query)} by creating query with type
	 * SentMessage.
	 */
	@Test
	public void testCreateSentMessageCommand() {
		ClientCommand command = ClientCommandFactory.createCommand(view, new Query(
				QueryTypes.SentMessage, "Hello"));

		assertTrue(command instanceof SentMessageCommand);
	}

	/**
	 * Tests {@link ClientCommandFactory#createCommand(View, Query)} by creating query with type
	 * ClientConnected.
	 */
	@Test
	public void testCreateClientConnectedCommand() {
		ClientCommand command = ClientCommandFactory.createCommand(view, new Query(
				QueryTypes.ClientConnected, "test"));

		assertTrue(command instanceof ClientConnectedCommand);
	}

	/**
	 * Tests {@link ClientCommandFactory#createCommand(View, Query)} by creating query with type
	 * ClientDisconnected.
	 */
	@Test
	public void testCreateClientDisconnectedCommand() {
		ClientCommand command = ClientCommandFactory.createCommand(view, new Query(
				QueryTypes.ClientDisconnected, "test"));

		assertTrue(command instanceof ClientDisconnectedCommand);
	}

	/**
	 * Tests {@link ClientCommandFactory#createCommand(View, Query)} by creating query with type
	 * Closed.
	 */
	@Test
	public void testCreateClosedCommand() {
		ClientCommand command = ClientCommandFactory.createCommand(view, new Query(
				QueryTypes.Closed, "test"));

		assertTrue(command instanceof ClosedCommand);
	}

	/**
	 * Tests {@link ClientCommandFactory#createCommand(View, Query)} by creating query with type
	 * that the client does not handle.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNotServerCommand() {
		ClientCommandFactory.createCommand(view, new Query(QueryTypes.Login, "test"));
	}
}
