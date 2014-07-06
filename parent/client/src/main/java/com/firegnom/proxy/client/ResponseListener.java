package com.firegnom.proxy.client;

import com.firegnom.proxy.protocol.ServerMessage;

/**
 * The Interface ResponseListener is used by the client to pass response as soon
 * as it is passed back from the server.
 */
public interface ResponseListener {

	/**
	 * Response sent by the server.
	 *
	 * @param m
	 *            the m
	 */
	public void response(ServerMessage m);
}
