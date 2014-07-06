package com.firegnom.proxy;

/**
 * The Class ProxyRunner is a main class to set up Proxy server.
 */
public class ProxyRunner {
	
	private ProxyRunner() {
		// Hiding default constructor as this class should not be instanciated
	}
	
	/**
	 * The main method responsible for setting up Proxy server.
	 *
	 * @param args
	 *            the args
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		Proxy p = new Proxy();
		p.run();
	}

}
