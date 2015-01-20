package com.sirma.itt.javacourse.chatserver.server;

import java.util.List;

import com.sirma.itt.javacourse.chatcommon.models.Query;

/**
 * Defines contract for managing the server's clients.
 * 
 * @author Sinan
 */
public interface ServerManager {
	/**
	 * Adds a new {@link Client}.
	 * 
	 * @param client
	 *            - the client to be added to the server manager
	 */
	void addClient(Client client);

	/**
	 * Removes given client from the server.
	 * 
	 * @param client
	 *            - the client to be removed from the server
	 */
	void removeClient(Client client);

	/**
	 * Dispatches a {@link Query} to the all connected clients.
	 * 
	 * @param query
	 *            - the query to be dispatched to the all connected clients
	 */
	void dispatchQueryToAll(Query query);

	/**
	 * Checks if client exist with given nickname.
	 * 
	 * @param nickname
	 *            - the nickname of a client to be checked if exists
	 * @return - true if client with given nickname exits; otherwise false
	 */
	boolean containsClient(String nickname);

	/**
	 * Clears the clients in the server manager.
	 */
	void clear();

	/**
	 * Returns a {@link List} of the connected clients.
	 * 
	 * @return - a list of the connected clients
	 */
	List<Client> getClientsList();

	/**
	 * Returns the number of connected clients.
	 * 
	 * @return - the number of connected clients
	 */
	int getNumberOfOnlineClients();

	/**
	 * Returns all connected clients nicknames separated by space.
	 * 
	 * @return all connected clients nicknames separated by space.
	 */
	String getOnlineClientsNicknames();

	/**
	 * Registers the given {@link SocketsManager} to the server manager.
	 * 
	 * @param queryHandlerManager
	 *            - the query handler manager to be registered
	 */
	void registerSocketsManager(SocketsManager queryHandlerManager);
}
