package com.sirma.itt.javacourse.chatclient.views;

/**
 * @author Sinan
 */
public interface View {

	/**
	 * Appends a given message to the chat area.
	 * 
	 * @param message
	 *            - the message to be appended to the chat area
	 */
	void appendMessageToChatArea(String message);

	/**
	 * Adds a given nickname to the online clients list.
	 * 
	 * @param nickname
	 *            - the nickname to be appended to the online clients list
	 */
	void addOnlineClient(String nickname);

	/**
	 * Removes given client nickname from the online clients list.
	 * 
	 * @param nickname
	 *            - the nickname of the client that will be removed
	 */
	void removeOnlineClient(String nickname);

	/**
	 * Shows an error dialog with given message.
	 * 
	 * @param message
	 *            - the message to be shown in the error dialog
	 */
	void showErrorDialog(String message);

	/**
	 * Shows a notice dialog with given message.
	 * 
	 * @param message
	 *            - the message to be shown in the notice dialog
	 */
	void showNoticeDialog(String message);

	/**
	 * Resets view's user interface components (enabled/disabled).
	 */
	void resetUI();

	/**
	 * Clears the area of the online users in the view.
	 */
	void clearOnlineClientsList();

	void dispose();
}
