package com.firegnom.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ProxyRunner is a main class to set up Proxy server.
 */
public class ProxyRunner {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(Proxy.class);

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
		Proxy proxy = null;
		switch (args.length) {
		case 0:
			LOG.info("Starting proxy server using default configuration");
			proxy = new Proxy();
			break;
		case 1:
			proxy = new Proxy(args[0]);
			break;
		default:
			LOG.info(usage());
			System.exit(-1);
			break;
		}
		proxy.run();
	}

	private static String usage() {
		return "Usage: ProxyRunner [config]\n"
				+ "[config] - is a proxy configuration file name stored "
				+ "somewhere in classpath ex.: /proxy.properties \n";
	}

}
