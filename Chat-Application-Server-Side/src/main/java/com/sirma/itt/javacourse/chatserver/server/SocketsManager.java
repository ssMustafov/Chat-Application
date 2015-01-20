package com.sirma.itt.javacourse.chatserver.server;

import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;

/**
 * Defines contract for managing connected clients's {@link QueryHandler}s.
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
	 * Adds a new {@link QueryHandler} and id of the {@link Client}.
	 * 
	 * @param id
	 *            - the id of the client
	 * @param queryHandler
	 *            - the query handler of the client
	 */
	void add(int id, QueryHandler queryHandler);

	/**
	 * Removes the {@link Client}'s {@link QueryHandler}.
	 * 
	 * @param id
	 *            - the id of the client
	 */
	void remove(int id);

	/**
	 * Clears the handler manager.
	 */
	void clear();
}
