package com.sirma.itt.javacourse.chatserver.server;

import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;

/**
 * @author Sinan
 */
public interface SocketsManager {
	QueryHandler getHandler(int id);

	void add(int id, QueryHandler queryHandler);

	void clear();
}
