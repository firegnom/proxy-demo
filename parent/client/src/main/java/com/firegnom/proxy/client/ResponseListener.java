package com.firegnom.proxy.client;

import com.firegnom.proxy.protocol.ServerMessage;

public interface ResponseListener {
	public void response(ServerMessage m);
}
