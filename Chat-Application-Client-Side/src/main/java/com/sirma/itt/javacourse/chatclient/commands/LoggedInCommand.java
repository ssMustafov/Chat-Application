package com.sirma.itt.javacourse.chatclient.commands;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.utils.Query;

/**
 * @author smustafov
 */
public class LoggedInCommand extends ClientCommand {

	private Query query;

	/**
	 * @param view
	 */
	public LoggedInCommand(View view, Query query) {
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
