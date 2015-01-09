package com.sirma.itt.javacourse.chatserver.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sirma.itt.javacourse.chatcommon.utils.Query;
import com.sirma.itt.javacourse.chatcommon.utils.QueryTypes;
import com.sirma.itt.javacourse.chatserver.server.ServerDispatcher;
import com.sirma.itt.javacourse.chatserver.views.MockView;
import com.sirma.itt.javacourse.chatserver.views.View;

/**
 * Tests {@link com.sirma.itt.javacourse.chatserver.commands.ServerCommandFactory} class.
 * 
 * @author smustafov
 */
public class ServerCommandFactoryTest {

	private ServerDispatcher serverDispatcher = new ServerDispatcher();
	private View view = new MockView();

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatserver.commands.ServerCommandFactory#createCommand(com.sirma.itt.javacourse.chatserver.server.ServerDispatcher, com.sirma.itt.javacourse.chatserver.views.View, com.sirma.itt.javacourse.chatcommon.utils.Query)}
	 * by giving send message query.
	 */
	@Test
	public void testCreateSendMessageCommand() {
		ServerCommand command = ServerCommandFactory.createCommand(serverDispatcher, view,
				new Query(QueryTypes.SendMessage, "helloo"));

		assertTrue(command instanceof SendMessageCommand);
	}

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatserver.commands.ServerCommandFactory#createCommand(com.sirma.itt.javacourse.chatserver.server.ServerDispatcher, com.sirma.itt.javacourse.chatserver.views.View, com.sirma.itt.javacourse.chatcommon.utils.Query)}
	 * by giving login query.
	 */
	@Test
	public void testCreateLoginCommand() {
		ServerCommand command = ServerCommandFactory.createCommand(serverDispatcher, view,
				new Query(QueryTypes.Login, "client"));

		assertTrue(command instanceof LoginCommand);
	}

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatserver.commands.ServerCommandFactory#createCommand(com.sirma.itt.javacourse.chatserver.server.ServerDispatcher, com.sirma.itt.javacourse.chatserver.views.View, com.sirma.itt.javacourse.chatcommon.utils.Query)}
	 * by giving logout query.
	 */
	@Test
	public void testCreateLogoutCommand() {
		ServerCommand command = ServerCommandFactory.createCommand(serverDispatcher, view,
				new Query(QueryTypes.Logout, "client"));

		assertTrue(command instanceof LogoutCommand);
	}

	/**
	 * Tests
	 * {@link com.sirma.itt.javacourse.chatserver.commands.ServerCommandFactory#createCommand(com.sirma.itt.javacourse.chatserver.server.ServerDispatcher, com.sirma.itt.javacourse.chatserver.views.View, com.sirma.itt.javacourse.chatcommon.utils.Query)}
	 * by giving a command that the server does not handle.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNotServerCommand() {
		ServerCommandFactory.createCommand(serverDispatcher, view, new Query(QueryTypes.Closed,
				"server"));
	}

}
