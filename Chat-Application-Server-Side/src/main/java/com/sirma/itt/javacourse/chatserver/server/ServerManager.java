package com.sirma.itt.javacourse.chatserver.server;

import java.util.List;

import com.sirma.itt.javacourse.chatcommon.models.Client;
import com.sirma.itt.javacourse.chatcommon.models.Query;

/**
 * @author Sinan
 */
public interface ServerManager {
	void addClient(Client client);

	void removeClient(Client client);

	void dispatchQueryToAll(Query query);

	boolean containsClient(String nickname);

	void clear();

	List<Client> getClientsList();

	int getNumberOfOnlineClients();

	String getOnlineClientsNicknames();

	void registerSocketsManager(SocketsManager socketsManager);
}
