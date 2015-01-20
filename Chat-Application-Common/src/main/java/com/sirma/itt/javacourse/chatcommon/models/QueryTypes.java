package com.sirma.itt.javacourse.chatcommon.models;

/**
 * Query types for communication between client and server.
 * 
 * @author Sinan
 */
public enum QueryTypes {
	// send by client
	Login, Logout, SendMessage,

	// send by server
	Refused, Closed, LoggedIn, LoggedOut, ClientsNicknames, ClientConnected, ClientDisconnected, Alive, SentMessage
}
