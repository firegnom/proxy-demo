package com.firegnom.proxy.server;

/**
 * The Class ServerRunner.
 */
public class ServerRunner {
	private static final int DEFAULT_PORT = 49001;

	/**
	 * The main method. starting server
	 * 
	 * @param args
	 *            the args
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server(DEFAULT_PORT);
		server.run();
	}

}
