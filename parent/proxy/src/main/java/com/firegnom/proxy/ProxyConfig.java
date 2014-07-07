package com.firegnom.proxy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ProxyConfig is responsible for reading properties file and seting
 * up defaults for missing configuration.
 */
public class ProxyConfig {

	/** The Constant FILE_NAME. */
	private static final String FILE_NAME = "/proxy.properties";

	/** The Constant LOG. */
	private final static Logger LOG = LoggerFactory
			.getLogger(ProxyConfig.class);

	// properties keys
	/** The Constant LOCAL_PORT. */
	private static final String LOCAL_PORT = "local.port";

	/** The Constant REMOTE_PORT. */
	private static final String REMOTE_PORT = "remote.port";

	/** The Constant REMOTE_HOST. */
	private static final String REMOTE_HOST = "remote.host";

	/** The Constant TIMEOUT. */
	private static final String TIMEOUT = "timeout";

	/** The Constant THREADS_BOSS. */
	private static final String THREADS_BOSS = "threads.boss";

	/** The Constant THREADS_WORKER. */
	private static final String THREADS_WORKER = "threads.worker";

	// defaults
	/** The Constant DEFALT_LOCAL_PORT. */
	private static final String DEFALT_LOCAL_PORT = "49000";

	/** The Constant DEFALT_REMOTE_HOST. */
	private static final String DEFALT_REMOTE_HOST = "localhost";

	/** The Constant DEFALT_REMOTE_PORT. */
	private static final String DEFALT_REMOTE_PORT = "49001";

	/** The Constant DEFALT_TIMEOUT. */
	private static final String DEFALT_TIMEOUT = "60";

	/** The Constant DEFALT_THREADS_BOSS. */
	private static final String DEFALT_THREADS_BOSS = "1";

	/** The Constant DEFALT_THREADS_WORKER. */
	private static final String DEFALT_THREADS_WORKER = "16";

	/** The properties. */
	Properties properties;

	/**
	 * The Constructor takes default config file , specified in FILE_NAME static
	 * field.
	 */
	public ProxyConfig() {
		init(FILE_NAME);
	}

	/**
	 * The Constructor takes as a parameter properties file name.
	 *
	 * @param fileName
	 *            the file name
	 */
	public ProxyConfig(String fileName) {
		init(fileName);
	}

	/**
	 * Inits the Proxy configuration loads properties file passed as a
	 * parameter.
	 *
	 * @param fileName
	 *            the file name
	 */
	private void init(String fileName) {
		LOG.info("Initialisation of proxy properties.");
		LOG.debug("Properties file : " + fileName);

		properties = new Properties();
		InputStream input = null;

		try {
			input = ProxyConfig.class.getResourceAsStream(fileName);
			properties.load(input);
		} catch (NullPointerException e){
			//If resource could not be found in classpath try file input stream
			try {
				input = new FileInputStream(fileName);
				properties.load(input);
			} catch (FileNotFoundException e1) {
				LOG.error("Can't find  properties file in specified path :" + FILE_NAME, e);
			} catch (IOException e1) {
				LOG.error("Can't load properties file in specified path :" + FILE_NAME, e);
			}
		
		} catch (FileNotFoundException e) {
			LOG.error("Can't find  properties file in classpath :" + FILE_NAME, e);
		} catch (IOException e) {
			LOG.error("Can't load properties file in classpath :" + FILE_NAME, e);
		} finally {
			// make sure we close everything
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					LOG.error("Can't close file input stream", e);
				}
			}
		}
	}

	/**
	 * Gets the local port.
	 *
	 * @return the local port
	 */
	public int getLocalPort() {
		return Integer.parseInt(properties.getProperty(LOCAL_PORT,
				DEFALT_LOCAL_PORT));
	}

	/**
	 * Gets the remote port.
	 *
	 * @return the remote port
	 */
	public int getRemotePort() {
		return Integer.parseInt(properties.getProperty(REMOTE_PORT,
				DEFALT_REMOTE_PORT));

	}

	/**
	 * Gets the remote host.
	 *
	 * @return the remote host
	 */
	public String getRemoteHost() {
		return properties.getProperty(REMOTE_HOST, DEFALT_REMOTE_HOST);
	}

	/**
	 * Gets the timeout for both incoming and outgoing connections.
	 *
	 * @return the timeout
	 */
	public int getTimeout() {
		return Integer
				.parseInt(properties.getProperty(TIMEOUT, DEFALT_TIMEOUT));

	}

	/**
	 * Gets the number of threads that should be created for the boss thread
	 * pool.
	 *
	 * @return the threads boss
	 */
	public int getThreadsBoss() {
		return Integer.parseInt(properties.getProperty(THREADS_BOSS,
				DEFALT_THREADS_BOSS));
	}

	/**
	 * Gets the number of threads that should be created for the worker thread
	 * pool.
	 *
	 * @return the threads worker
	 */
	public int getThreadsWorker() {
		return Integer.parseInt(properties.getProperty(THREADS_WORKER,
				DEFALT_THREADS_WORKER));
	}

}
