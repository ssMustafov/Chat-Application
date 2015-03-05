package com.sirma.itt.javacourse.chatserver.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sirma.itt.javacourse.chatcommon.models.Query;

/**
 * Holds all the {@link Client}s in a {@link List}. Also can dispatch queries to all clients.
 * 
 * @author Sinan
 */
public class ClientsManager implements Runnable, ServerManager {

	public static final String EMPTY_STRING = "";
	private static final Logger LOGGER = LogManager.getLogger(ClientsManager.class);
	private SocketsManager socketsManager;
	private List<Client> clients = new ArrayList<>();
	private Queue<Query> queriesQueue = new LinkedList<>();
	private Thread thisThread;

	/**
	 * Creates a new clients manager.
	 */
	public ClientsManager() {
		thisThread = new Thread(this);
		thisThread.start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerSocketsManager(SocketsManager socketsManager) {
		this.socketsManager = socketsManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addClient(Client client) {
		synchronized (clients) {
			clients.add(client);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeClient(Client client) {
		synchronized (clients) {
			clients.remove(client);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispatchQueryToAll(Query query) {
		synchronized (queriesQueue) {
			queriesQueue.add(query);
			queriesQueue.notify();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsClient(String nickname) {
		synchronized (clients) {
			for (Client client : clients) {
				String currentClientNickname = client.getNickname().toLowerCase();
				if (currentClientNickname.equals(nickname.toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		synchronized (clients) {
			clients.clear();
		}
		synchronized (queriesQueue) {
			queriesQueue.clear();
		}
		thisThread.interrupt();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized List<Client> getClientsList() {
		return clients;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized int getNumberOfOnlineClients() {
		return clients.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getOnlineClientsNicknames() {
		String nicknames = EMPTY_STRING;

		synchronized (clients) {
			if (clients.size() > 0) {
				StringBuilder result = new StringBuilder();
				for (int i = 0; i < clients.size() - 1; i++) {
					result.append(clients.get(i).getNickname());
					result.append(" ");
				}
				result.append(clients.get(clients.size() - 1).getNickname());

				nicknames = result.toString();
			}
		}

		return nicknames;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		while (true) {
			Query query = getNextQueryFromQueue();
			if (query != null) {
				sendQueryToAll(query);
			}
		}
	}

	/**
	 * Returns the next {@code Query} from the queue.
	 * 
	 * @return - the next query from the queue
	 */
	private Query getNextQueryFromQueue() {
		Query query = null;

		synchronized (queriesQueue) {
			if (queriesQueue.size() == 0) {
				try {
					queriesQueue.wait();
				} catch (InterruptedException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
			query = queriesQueue.poll();
		}

		return query;
	}

	/**
	 * Sends given {@code Query} to the all clients.
	 * 
	 * @param query
	 *            - the query that will be sent to all clients
	 * @param query
	 */
	private void sendQueryToAll(Query query) {
		synchronized (clients) {
			for (Client client : clients) {
				socketsManager.getHandler(client.getId()).sendQuery(query);
			}
		}
	}
}
