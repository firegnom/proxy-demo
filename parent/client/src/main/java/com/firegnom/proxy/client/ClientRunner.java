package com.firegnom.proxy.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.firegnom.proxy.protocol.ServerMessage;

/**
 * The Class ClientRunner.
 */
public class ClientRunner {

	/** The LOG. */
	private static final Logger LOG = LoggerFactory
			.getLogger(ClientRunner.class);

	/**
	 * The main method it takes 3 arguments
	 *
	 * @param args
	 *            the args
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		LOG.info("Initialising Client");
		Client c = new Client(49000);

		c.send(new ServerMessage("test message", 123), new ResponseListener() {
			@Override
			public void response(ServerMessage m) {
				LOG.info("Recieved from server number : " + m.getNumber()
						+ " and message :" + m.getMessage());

			}
		});

	}

	private static String usage() {
		return "Usage: ClientRunner [port] number message"
				+ "[port] - is a destinat";

	}
}
