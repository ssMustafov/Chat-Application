package com.sirma.itt.javacourse.chatserver.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sirma.itt.javacourse.chatcommon.models.Query;

/**
 * @author Sinan
 */
public class ClientsManager implements Runnable, ServerManager {

	public static final String EMPTY_STRING = "";
	private static final Logger LOGGER = LogManager.getLogger(ClientsManager.class);
	private SocketsManager socketsManager;
	private List<Client> clients = new ArrayList<>();
	private Queue<Query> queriesQueue = new LinkedList<>();

	public ClientsManager() {
		new Thread(this).start();
	}

	public void registerSocketsManager(SocketsManager socketsManager) {
		this.socketsManager = socketsManager;
	}

	@Override
	public void addClient(Client client) {
		synchronized (clients) {
			clients.add(client);
		}
	}

	@Override
	public void removeClient(Client client) {
		synchronized (clients) {
			clients.remove(client);
		}
	}

	@Override
	public void dispatchQueryToAll(Query query) {
		synchronized (queriesQueue) {
			queriesQueue.add(query);
			queriesQueue.notify();
		}
	}

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

	@Override
	public void clear() {
		synchronized (clients) {
			clients.clear();
		}
		synchronized (queriesQueue) {
			queriesQueue.clear();
		}
	}

	@Override
	public List<Client> getClientsList() {
		return clients;
	}

	@Override
	public int getNumberOfOnlineClients() {
		return clients.size();
	}

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

	@Override
	public void run() {
		while (true) {
			Query query = getNextQueryFromQueue();
			if (query != null) {
				sendQueryToAll(query);
			}
		}
	}

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

	private void sendQueryToAll(Query query) {
		synchronized (clients) {
			for (Client client : clients) {
				socketsManager.getHandler(client.getId()).sendQuery(query);
			}
		}
	}
}
