package com.sirma.itt.javacourse.chatclient.commands;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;

/**
 * @author Sinan
 */
public class SentMessageCommand extends ClientCommand {

	private Query query;

	/**
	 * @param view
	 */
	public SentMessageCommand(View view, Query query) {
		super(view);
		this.query = query;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		String message = query.getMessage();
		getClientView().appendMessageToChatArea(message);
	}

}
