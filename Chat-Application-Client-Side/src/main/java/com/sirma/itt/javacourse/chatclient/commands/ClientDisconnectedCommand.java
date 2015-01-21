package com.sirma.itt.javacourse.chatclient.commands;

import java.util.ResourceBundle;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.Query;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageBundleSingleton;
import com.sirma.itt.javacourse.chatcommon.utils.LanguageConstants;

/**
 * Handles the ClientDisconnectedCommand query from the server. Sent from the server when a new
 * client is disconnected.
 * 
 * @author Sinan
 */
public class ClientDisconnectedCommand extends ClientCommand {

	private ResourceBundle bundle = LanguageBundleSingleton.getClientBundleInstance();
	private Query query;

	/**
	 * Creates a new client disconnected command with given {@link View} of the client and server
	 * {@link Query}.
	 * 
	 * @param view
	 *            - the view of the client
	 * @param query
	 *            - the query sent from the server
	 */
	public ClientDisconnectedCommand(View view, Query query) {
		super(view);
		this.query = query;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		String nickname = query.getMessage();
		getClientView().removeOnlineClient(nickname);
		getClientView().appendMessageToChatArea(
				"@" + query.getMessage() + " "
						+ bundle.getString(LanguageConstants.CLIENT_LEFT_MESSAGE));
	}

}
