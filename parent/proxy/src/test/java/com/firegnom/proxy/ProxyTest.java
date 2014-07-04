package com.firegnom.proxy;

import static org.junit.Assert.*;

import org.junit.Test;

import com.firegnom.proxy.client.Client;
import com.firegnom.proxy.client.ResponseListener;
import com.firegnom.proxy.protocol.ServerMessage;
import com.firegnom.proxy.server.Server;

public class ProxyTest {
	class ProxtThread extends Thread {
		@Override
		public void run() {
			Proxy p = new Proxy("proxy_run.properties");
			try {
				p.run();
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			super.run();
		}
	}

	class ServerThread extends Thread {
		@Override
		public void run() {
			Server s = new Server(49001);
			try {
				s.run();
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			super.run();
		}
	}

	/** This field is used to determine if server recieaved response */
	boolean failed;

	/**
	 * This test starts proxy server on default port 49000 , after that it
	 * starts server on port 49001 both in separate threads. After that it waits
	 * for one second and tries to send a message to server via client , it will
	 * fail if there was no message from the server or message was incorrect
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testProxy() throws Exception {

		ProxtThread proxy = new ProxtThread();
		ServerThread server = new ServerThread();
		proxy.start();
		server.start();
		Thread.sleep(1000);

		failed = true;
		Client client = new Client(49000);
		client.send(new ServerMessage("test message", 123),
				new ResponseListener() {
					@Override
					public void response(ServerMessage m) {
						assertEquals(444, m.getNumber());
						assertEquals("test message <SEEN BY SERVER>",
								m.getMessage());
						failed = false;
					}
				});

		if (failed)
			fail();

	}

}
