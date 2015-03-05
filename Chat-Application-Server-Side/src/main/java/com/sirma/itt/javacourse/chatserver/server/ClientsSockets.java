package com.sirma.itt.javacourse.chatserver.server;

import java.util.HashMap;
import java.util.Map;

import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;

/**
 * Holds all clients {@link QueryHandler}s.
 * 
 * @author Sinan
 */
public class ClientsSockets implements SocketsManager {

	private Map<Integer, QueryHandler> clients = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QueryHandler getHandler(int id) {
		QueryHandler handler = null;
		synchronized (clients) {
			handler = clients.get(id);
		}

		return handler;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(int id, QueryHandler queryHandler) {
		synchronized (clients) {
			clients.put(id, queryHandler);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(int id) {
		synchronized (clients) {
			clients.remove(id);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		synchronized (clients) {
			clients.clear();
		}
	}

}
