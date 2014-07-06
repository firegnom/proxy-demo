package com.firegnom.proxy.server;

/**
 * The Class ServerRunner.
 */
public class ServerRunner {
	/**
	 * The main method. starting server
	 * 
	 * @param args
	 *            the args
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server(49001);
		server.run();
	}

}
