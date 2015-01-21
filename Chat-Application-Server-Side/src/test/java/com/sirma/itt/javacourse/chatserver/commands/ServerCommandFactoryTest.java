package com.sirma.itt.javacourse.chatserver.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.models.QueryTypes;
import com.sirma.itt.javacourse.chatserver.server.ServerManager;
import com.sirma.itt.javacourse.chatserver.server.SocketsManager;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Tests {@link com.sirma.itt.javacourse.chatserver.commands.ServerCommandFactory} class.
 * 
 * @author smustafov
 */
public class ServerCommandFactoryTest {

	private ServerManager serverManager;
	private SocketsManager socketManager;
	private View view;

	/**
	 * 
	 */
	@Before
	public void setUp() {
		view = Mockito.mock(View.class);
		serverManager = Mockito.mock(ServerManager.class);
		socketManager = Mockito.mock(SocketsManager.class);
	}

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatserver.commands.ServerCommandFactory#createCommand(com.sirma.itt.javacourse.chatserver.server.ServerDispatcher, com.sirma.itt.javacourse.chatserver.views.View, com.sirma.itt.javacourse.chatcommon.utils.Query)}
	 * by giving send message query.
	 */
	@Test
	public void testCreateSendMessageCommand() {
		ServerCommand command = ServerCommandFactory.createCommand(serverManager, socketManager,
				view, new Query(QueryTypes.SendMessage, "helloo"));

		assertTrue(command instanceof SendMessageCommand);
	}

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatserver.commands.ServerCommandFactory#createCommand(com.sirma.itt.javacourse.chatserver.server.ServerDispatcher, com.sirma.itt.javacourse.chatserver.views.View, com.sirma.itt.javacourse.chatcommon.utils.Query)}
	 * by giving login query.
	 */
	@Test
	public void testCreateLoginCommand() {
		ServerCommand command = ServerCommandFactory.createCommand(serverManager, socketManager,
				view, new Query(QueryTypes.Login, "client"));

		assertTrue(command instanceof LoginCommand);
	}

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatserver.commands.ServerCommandFactory#createCommand(com.sirma.itt.javacourse.chatserver.server.ServerDispatcher, com.sirma.itt.javacourse.chatserver.views.View, com.sirma.itt.javacourse.chatcommon.utils.Query)}
	 * by giving logout query.
	 */
	@Test
	public void testCreateLogoutCommand() {
		ServerCommand command = ServerCommandFactory.createCommand(serverManager, socketManager,
				view, new Query(QueryTypes.Logout, "client"));

		assertTrue(command instanceof LogoutCommand);
	}

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatserver.commands.ServerCommandFactory#createCommand(com.sirma.itt.javacourse.chatserver.server.ServerDispatcher, com.sirma.itt.javacourse.chatserver.views.View, com.sirma.itt.javacourse.chatcommon.utils.Query)}
	 * by giving a command that the server does not handle.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNotClientCommand() {
		ServerCommandFactory.createCommand(serverManager, socketManager, view, new Query(
				QueryTypes.Closed, "server"));
	}

}