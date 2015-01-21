package com.sirma.itt.javacourse.chatclient.commands;

import java.util.ResourceBundle;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;

/**
 * Handles the Closed query from the server. Sent from the server when the server is closed in a
 * normal way.
 * 
 * @author Sinan
 */
public class ClosedCommand extends ClientCommand {

	private ResourceBundle bundle = LanguageBundleSingleton.getClientBundleInstance();
	private Query query;

	/**
	 * Creates a new closed command with given {@link View} and server {@link Query}.
	 * 
	 * @param view
	 *            - the view of the client
	 * @param query
	 *            - the query sent from the server
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
		String message = bundle.getString(query.getMessage());
		getClientView().showErrorDialog(message);
	}

}
