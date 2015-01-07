package com.sirma.itt.javacourse.chatclient.commands;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.utils.Query;

/**
 * @author smustafov
 */
public class SuccesCommand extends ClientCommand {

	private Query query;

	/**
	 * @param view
	 * @param query
	 */
	public SuccesCommand(View view, Query query) {
		super(view);
		this.query = query;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		getClientView().appendMessageToChatArea(query.getMessage());
	}

}
