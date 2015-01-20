package com.sirma.itt.javacourse.chatcommon.models;

/**
 * @author Sinan
 */
public enum QueryTypes {
	// send by client
	Login, Logout, SendMessage, SendPrivateMessage,

	// send by server
	Refused, Closed, LoggedIn, LoggedOut, ClientsNicknames, ClientConnected, ClientDisconnected, Alive, SentMessage, SentPrivateMessage
}
