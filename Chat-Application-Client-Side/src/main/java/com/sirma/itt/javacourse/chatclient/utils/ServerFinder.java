package com.sirma.itt.javacourse.chatclient.utils;

import java.io.IOException;
import java.net.Socket;

import com.sirma.itt.javacourse.chatcommon.utils.ServerConfig;

/**
 * Holds method for finding the server's socket.
 * 
 * @author Sinan
 */
public final class ServerFinder {

	private static int[] serverPorts;

	/**
	 * Protects from instantiation.
	 */
	private ServerFinder() {

	}

	static {
		serverPorts = parseStringArrayToIntArray(ServerConfig.SERVER_PORTS);
	}

	/**
	 * Parses given string array into int array.
	 * 
	 * @param array
	 *            - the array of string to be parsed
	 * @return - the parsed to int array, string array
	 */
	private static int[] parseStringArrayToIntArray(String[] array) {
		int[] parsedArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			parsedArray[i] = Integer.parseInt(array[i]);
		}
		return parsedArray;
	}

	/**
	 * Finds and connects to the server. It searches for server in the ports range defined in
	 * {@link ServerConfig#SERVER_PORTS}.
	 * 
	 * @return - the socket of the server
	 * @throws IOException
	 *             - thrown when there is no running server in the ports range
	 */
	public static Socket openSocket() throws IOException {
		for (int i = 0; i < serverPorts.length; i++) {
			try {
				return new Socket("localhost", serverPorts[i]);
			} catch (IOException e) {
				// try the next port
			}
		}

		throw new IOException(String.format("No running server sockets in ports range [%s...%s]",
				serverPorts[0], serverPorts[serverPorts.length - 1]));
	}

}
