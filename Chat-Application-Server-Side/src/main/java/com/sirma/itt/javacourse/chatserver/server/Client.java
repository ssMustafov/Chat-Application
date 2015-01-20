package com.sirma.itt.javacourse.chatserver.server;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Sinan
 */
public class Client {

	private static AtomicInteger atomicId = new AtomicInteger();
	private int id;
	private String nickname = "";

	public Client(String nickname) {
		id = atomicId.incrementAndGet();
		this.nickname = nickname;
	}

	public Client() {
		id = atomicId.incrementAndGet();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getId() {
		return id;
	}

}
