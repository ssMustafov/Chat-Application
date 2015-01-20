package com.sirma.itt.javacourse.chatclient.commands;

import com.sirma.itt.javacourse.chatclient.views.View;

/**
 * @author Sinan
 */
public class LoggedOutCommand extends ClientCommand {

	/**
	 * @param view
	 */
	public LoggedOutCommand(View view) {
		super(view);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		getClientView().clearOnlineClientsList();
		getClientView().resetUI();
	}

}
