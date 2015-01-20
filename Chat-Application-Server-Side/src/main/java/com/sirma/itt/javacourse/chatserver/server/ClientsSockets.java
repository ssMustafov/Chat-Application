package com.sirma.itt.javacourse.chatserver.server;

import java.util.HashMap;
import java.util.Map;

import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;

/**
 * @author Sinan
 */
public class ClientsSockets implements SocketsManager {
	private Map<Integer, QueryHandler> clients = new HashMap<>();

	@Override
	public QueryHandler getHandler(int id) {
		return clients.get(id);
	}

	@Override
	public void add(int id, QueryHandler queryHandler) {
		clients.put(id, queryHandler);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(int id) {
		clients.remove(id);
	}

	@Override
	public void clear() {
		clients.clear();
	}

}
