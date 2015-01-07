package com.sirma.itt.javacourse.chatclient.utils;

import java.io.IOException;
import java.net.Socket;

import javax.swing.SwingWorker;

import com.sirma.itt.javacourse.chatcommon.utils.ServerConfig;

/**
 * @author Sinan
 */
public class ServerFinderThread extends SwingWorker<Void, Void> {

	private Socket socket;
	private int[] serverPorts;

	/**
	 * 
	 */
	public ServerFinderThread() {
		serverPorts = parseStringArrayToIntArray(ServerConfig.SERVER_PORTS);
	}

	/**
	 * Parses given string array into int array.
	 * 
	 * @param array
	 *            - the array of string to be parsed
	 * @return - the parsed to int array, string array
	 */
	private int[] parseStringArrayToIntArray(String[] array) {
		int[] parsedArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			parsedArray[i] = Integer.parseInt(array[i]);
		}
		return parsedArray;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Void doInBackground() throws Exception {
		for (int i = 0; i < serverPorts.length; i++) {
			try {
				socket = new Socket("localhost", serverPorts[i]);
			} catch (IOException e) {
				// try the next port
			}
		}
		if (socket != null) {
			setProgress(1);
		} else {
			setProgress(0);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void done() {

	}

}
