package com.sirma.itt.javacourse.chatserver.server;

import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;

/**
 * Defines contract for managing connected clients's sockets.
 * 
 * @author Sinan
 */
public interface SocketsManager {

	/**
	 * Returns the client's {@link QueryHandler} with given client id.
	 * 
	 * @param id
	 *            - the id of the client
	 * @return - the query handler of the client
	 */
	QueryHandler getHandler(int id);

	/**
	 * Adds a new .
	 * 
	 * @param id
	 * @param queryHandler
	 */
	void add(int id, QueryHandler queryHandler);

	void remove(int id);

	void clear();
}
