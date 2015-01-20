package com.sirma.itt.javacourse.chatclient.commands;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;

/**
 * Handles sent from the server connected client's nicknames.
 * 
 * @author Sinan
 */
public class ClientsNicknames extends ClientCommand {

	private Query query;

	/**
	 * Creates a new clients nicknames handler.
	 * 
	 * @param view
	 *            - the view of the client
	 * @param query
	 *            - the query from the server
	 */
	public ClientsNicknames(View view, Query query) {
		super(view);
		this.query = query;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		String[] nicknames = query.getMessage().split("\\s");
		for (int i = 0; i < nicknames.length; i++) {
			getClientView().addOnlineClient(nicknames[i]);
		}
	}

}
