package com.sirma.itt.javacourse.chatclient.client;

import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.sirma.itt.javacourse.chatclient.views.View;
import com.sirma.itt.javacourse.chatcommon.models.QueryHandler;

/**
 * @author Sinan
 */
public class ClientTest {

	private View view;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		view = Mockito.mock(View.class);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Client client = new Client(new QueryHandler(new Socket()), "test");
	}

}
