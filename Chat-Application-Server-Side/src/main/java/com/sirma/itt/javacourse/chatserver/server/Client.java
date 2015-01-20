package com.sirma.itt.javacourse.chatserver.server;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Client model. Every client has unique ID and nickname.
 * 
 * @author Sinan
 */
public class Client {

	private static AtomicInteger atomicId = new AtomicInteger();
	private int id;
	private String nickname = "";

	/**
	 * Creates a new client with given nickname.
	 * 
	 * @param nickname
	 *            - the nickname of the client to be set
	 */
	public Client(String nickname) {
		id = atomicId.incrementAndGet();
		this.nickname = nickname;
	}

	/**
	 * Creates a new client with empty nickname.
	 */
	public Client() {
		id = atomicId.incrementAndGet();
	}

	/**
	 * Returns the client's nickname.
	 * 
	 * @return - the client's nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Sets the client's nickname.
	 * 
	 * @param nickname
	 *            - the nickname to be set to the client
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Returns the client's unique ID.
	 * 
	 * @return - the client's unique ID
	 */
	public int getId() {
		return id;
	}

}
