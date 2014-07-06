package com.firegnom.proxy;

/**
 * The Class ProxyRunner.
 */
public class ProxyRunner {
	/**
	 * The main method.
	 *
	 * @param args
	 *            the args
	 * @throws Exception
	 *             the exception
	 */
	//TODO move this to separate class ProxyRunner as it is not a vital part of the Proxy
	public static void main(String[] args) throws Exception {
		Proxy p = new Proxy();
		p.run();
	}

}
