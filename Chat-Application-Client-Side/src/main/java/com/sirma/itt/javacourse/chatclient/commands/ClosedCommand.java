package com.sirma.itt.javacourse.chatclient.commands;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;

/**
 * @author Sinan
 */
public class ClosedCommand extends ClientCommand {

	private Query query;

	/**
	 * @param view
	 */
	public ClosedCommand(View view, Query query) {
		super(view);
		this.query = query;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		getClientView().clearOnlineClientsList();
		getClientView().resetUI();
		getClientView().showErrorDialog(query.getMessage());
	}

}
