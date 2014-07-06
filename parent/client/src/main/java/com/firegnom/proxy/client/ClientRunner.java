package com.firegnom.proxy.client;

import java.text.ParseException;

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

	public ClientRunner() {
		// Hiding default constructor as this class should not be instanciated
	}

	/**
	 * The main method it takes 3 arguments
	 *
	 * @param args
	 *            the args
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		//check if passed corect number of arguments
		if (args.length != 4) {
			LOG.error(usage());
			System.exit(-1);

		}
		//read and parse arguments 
		String host="", message="";
		int port=0, number=0;
		try {
			host = args[0];
			port = Integer.parseInt(args[1]);
			number = Integer.parseInt(args[2]);
			message = args[3];

		} catch (NumberFormatException e) {
			LOG.error("Error while parsing parameters", e);
			LOG.error(usage());
			System.exit(-1);
		}
		
		LOG.info("Initialising Client");
		Client c = new Client(port, host);

		c.send(new ServerMessage(message, number), new ResponseListener() {
			@Override
			public void response(ServerMessage m) {
				LOG.info("Recieved from server number : " + m.getNumber()
						+ " and message :" + m.getMessage());

			}
		});

	}

	private static String usage() {
		return "Usage: ClientRunner host port number message\n"
				+ "host - is a server hostname\n"
				+ "port - is a server port number (normally it is 49000 for proxy and 49001 for server)\n"
				+ "number  - a number passed to server in the message\n"
				+ "message - a string message passed to the server in the message\n";

	}
}
