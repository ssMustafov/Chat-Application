package com.sirma.itt.javacourse.chatserver.views;

import java.util.Observable;
import java.util.Observer;

import com.sirma.itt.javacourse.chatserver.server.Server;

/**
 * Observes the view for choosing port, starting and stopping the server. By default the port is
 * 7000
 * 
 * @author Sinan
 */
public class ViewObserver implements Observer {

	private Server server;
	private int port = 7000;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg.getClass().equals(Integer.class)) {
			port = (int) arg;
		} else {
			String cmd = (String) arg;
			if (View.START_BUTTON_ACTION_COMMAND.equals(cmd)) {
				View view = (View) o;
				server = new Server(view, port);
				server.startServer();
			} else {
				server.stopServer();
			}
		}
	}
}
