package com.sirma.itt.javacourse.chatclient.commands;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;

/**
 * @author Sinan
 */
public class ClientConnectedCommand extends ClientCommand {

	private Query query;

	/**
	 * @param view
	 */
	public ClientConnectedCommand(View view, Query query) {
		super(view);
		this.query = query;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		String nickname = query.getMessage();
		getClientView().addOnlineClient(nickname);
	}

}
