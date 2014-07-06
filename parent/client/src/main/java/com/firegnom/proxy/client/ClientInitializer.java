package com.firegnom.proxy.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

import com.firegnom.proxy.protocol.ServerMessage;
import com.firegnom.proxy.protocol.ServerMessageDecoder;
import com.firegnom.proxy.protocol.ServerMessageEncoder;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {

	private static final int TIMEOUT = 60;
	private ServerMessage message;
	private ResponseListener responseListener;

	public ClientInitializer(ServerMessage message,
			ResponseListener responseListener) {
		this.message = message;
		this.responseListener = responseListener;
	}

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new ReadTimeoutHandler(TIMEOUT));
		p.addLast(new WriteTimeoutHandler(TIMEOUT));
		// Setup Encoder for server message it is used for sending server
		// message
		p.addLast(new ServerMessageEncoder());
		// Setup Decoder for server message it is used for receiving message
		// from the server
		p.addLast(new ServerMessageDecoder());
		p.addLast(new ClientHandler(message, responseListener));
	}

}
