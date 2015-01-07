package com.sirma.itt.javacourse.chatserver.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.sirma.itt.javacourse.chatcommon.utils.Query;
import com.sirma.itt.javacourse.chatcommon.utils.QueryTypes;

/**
 * Contains the {@code Client}s of the server.
 * 
 * @author Sinan
 */
public class ServerDispatcher extends Thread {
	private List<Client> clients = Collections.synchronizedList(new ArrayList<Client>());
	private Queue<Query> queriesQueue = new LinkedList<>();

	/**
	 * Adds a new {@code Client} to the server.
	 * 
	 * @param client
	 *            - the client to be added to the server
	 */
	public synchronized void addClient(Client client) {
		clients.add(client);
	}

	/**
	 * Removes given client from the server.
	 * 
	 * @param client
	 *            - the client about to be removed from the server
	 */
	public synchronized void removeClient(Client client) {
		clients.remove(client);
	}

	/**
	 * Dispatches a {@code Query} which will be sent to the all clients.
	 * 
	 * @param query
	 *            - the query which will be sent to all clients
	 */
	public synchronized void dispatchQuery(Query query) {
		queriesQueue.add(query);
		notify();
	}

	/**
	 * Checks if the server contains a client with the same nickname (case insensitive).
	 * 
	 * @param nickname
	 *            - the nickname to be checked if there is client with that nickname
	 * @return - true if the server does not contain client with the given nickname, otherwise
	 *         returns false
	 */
	public synchronized boolean containsClient(String nickname) {
		for (Client client : clients) {
			String currentClientNickname = client.getNickname().toLowerCase();
			if (currentClientNickname.equals(nickname.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Clears the server's clients list and queries queue.
	 */
	public void clear() {
		synchronized (clients) {
			for (Client client : clients) {
				client.sendQuery(new Query(QueryTypes.Closed, "The server was closed"));
				client.closeClient();
			}
			clients.clear();
		}
		synchronized (queriesQueue) {
			queriesQueue.clear();
		}
	}

	/**
	 * Returns the connected clients list.
	 * 
	 * @return - the connected clients list
	 */
	public List<Client> getClientsList() {
		return clients;
	}

	/**
	 * Returns the number of currently connected clients.
	 * 
	 * @return - the number of currently connected clients
	 */
	public int getNumberOfOnlineClients() {
		return clients.size();
	}

	/**
	 * Returns the online clients nicknames, separated by |.
	 * 
	 * @return - the online clients nicknames. If there is not online clients returns empty string
	 */
	public String getOnlineClientsNicknames() {
		if (clients.size() > 0) {
			StringBuilder result = new StringBuilder();
			synchronized (clients) {
				for (int i = 0; i < clients.size() - 1; i++) {
					result.append(clients.get(i).getNickname());
					result.append(" ");
				}
				result.append(clients.get(clients.size() - 1).getNickname());

				return result.toString();
			}
		}

		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		try {
			while (true) {
				Query query = getNextQueryFromQueue();
				sendQueryToAll(query);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the next {@code Query} from the queue.
	 * 
	 * @return - the next query from the queue
	 * @throws InterruptedException
	 *             - thrown when
	 */
	private synchronized Query getNextQueryFromQueue() throws InterruptedException {
		if (queriesQueue.size() == 0) {
			wait();
		}
		Query query = queriesQueue.poll();
		return query;
	}

	/**
	 * Sends given {@code Query} to the all clients.
	 * 
	 * @param query
	 *            - the query that will be sent to all clients
	 */
	private void sendQueryToAll(Query query) {
		synchronized (clients) {
			for (Client client : clients) {
				client.sendQuery(query);
			}
		}
	}

}
