package com.sirma.itt.javacourse.chatcommon.utils;

/**
 * Holds constants for server configuration.
 * 
 * @author Sinan
 */
public final class ServerConfig {

	/**
	 * Protects from instantiation.
	 */
	private ServerConfig() {

	}

	/**
	 * 15 minutes timeout.
	 */
	public static final int CLIENT_TIMEOUT = 900000;

	public static final int CLIENT_CONNECTIONS_MAX_SIZE = 20;

	public static final int CLIENT_CHAT_MESSAGE_MAX_LENGTH = 200;

	public static final String HOST = "localhost";
	public static final String[] SERVER_PORTS = { "7000", "7001", "7002", "7003", "7004", "7005" };
	public static final String[] AVAILABLE_LANGUAGES = { "English", "Български" };

}
