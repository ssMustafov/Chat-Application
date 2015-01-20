package com.sirma.itt.javacourse.chatserver.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sirma.itt.javacourse.chatcommon.models.Client;

/**
 * @author Sinan
 */
public class ClientsManagerTest {

	/**
	 * Tests client manager by adding two clients and checking their nicknames from the manager.
	 */
	@Test
	public void testGetOnlineClientsNicknamesWithTwoClients() {
		ServerManager manager = new ClientsManager();
		manager.addClient(new Client("gosho"));
		manager.addClient(new Client("test"));

		String clientsNicknames = manager.getOnlineClientsNicknames();

		assertEquals("gosho test", clientsNicknames);
	}

	/**
	 * Tests client manager by adding two clients then removing one of them.
	 */
	@Test
	public void testRemoveClient() {
		ClientsManager manager = new ClientsManager();

		Client client1 = new Client("gosho");
		Client client2 = new Client("test");
		manager.addClient(client1);
		manager.addClient(client2);

		manager.removeClient(client1);

		String clientsNicknames = manager.getOnlineClientsNicknames();

		assertEquals("test", clientsNicknames);
		assertFalse(manager.containsClient("gosho"));
	}

	/**
	 * Tests client manager with no clients and checking nicknames from the manager.
	 */
	@Test
	public void testGetOnlineClientsNicknamesWithNoClients() {
		ClientsManager manager = new ClientsManager();
		String clientsNicknames = manager.getOnlineClientsNicknames();

		assertEquals(ClientsManager.EMPTY_STRING, clientsNicknames);
	}

	/**
	 * Tests the client manager by adding three clients and checking not existing client nickname.
	 */
	@Test
	public void testContainsNotExisitingNicknameWithThreeClients() {
		ClientsManager manager = new ClientsManager();

		Client client1 = new Client("gosho");
		Client client2 = new Client("test");
		Client client3 = new Client("junior");
		manager.addClient(client1);
		manager.addClient(client2);
		manager.addClient(client3);

		assertFalse(manager.containsClient("testt"));
	}

	/**
	 * Tests the client manager by adding three clients and checking their nicknames does exist.
	 */
	@Test
	public void testContainsExisitingNicknamesWithThreeClients() {
		ClientsManager manager = new ClientsManager();

		Client client1 = new Client("gosho");
		Client client2 = new Client("test");
		Client client3 = new Client("junior");
		manager.addClient(client1);
		manager.addClient(client2);
		manager.addClient(client3);

		assertTrue(manager.containsClient("test"));
		assertTrue(manager.containsClient("JuNior"));
		assertTrue(manager.containsClient("GOSHO"));
	}

	/**
	 * Tests client manager with no clients if some exists.
	 */
	@Test
	public void testContainsClientNicknameWithNoClients() {
		ClientsManager manager = new ClientsManager();

		assertFalse(manager.containsClient("test"));
		assertEquals(0, manager.getNumberOfOnlineClients());
	}
}
