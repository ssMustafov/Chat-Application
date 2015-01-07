package com.sirma.itt.javacourse.chatserver.server;

import java.util.LinkedList;
import java.util.Queue;

import com.sirma.itt.javacourse.chatcommon.utils.Query;

/**
 * @author Sinan
 */
public class ClientWriterThread extends Thread {

	private ServerDispatcher serverDispatcher;
	private Client client;
	private Queue<Query> queriesQueue = new LinkedList<>();

	public ClientWriterThread(ServerDispatcher serverDispatcher, Client client) {
		this.serverDispatcher = serverDispatcher;
		this.client = client;
	}

	public synchronized void sendQuery(Query query) {
		queriesQueue.add(query);
		notify();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		try {
			while (!isInterrupted()) {
				Query query = getNextQueryFromQueue();
				client.sendQuery(query);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		serverDispatcher.removeClient(client);
	}

	private synchronized Query getNextQueryFromQueue() throws InterruptedException {
		while (queriesQueue.size() == 0) {
			wait();
		}
		return queriesQueue.poll();
	}

}
