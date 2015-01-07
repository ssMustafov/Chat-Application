package com.sirma.itt.javacourse.chatcommon.utils;

/**
 * Holds query types for the {@link Query}. Client and server communicates through so called
 * {@link Query}.
 * 
 * @see Query
 * @author Sinan
 */
public enum QueryTypes {
	// Queries send by the server
	SentMessage, Refused, Failed, Closed, Success, ClientConnected, ClientDisconnected, LoggedOut, LoggedIn,

	// Queries send by the client
	Login, Logout, SendMessage
}
