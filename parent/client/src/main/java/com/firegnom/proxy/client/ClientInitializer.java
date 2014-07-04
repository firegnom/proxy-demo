package com.firegnom.proxy.client;

import com.firegnom.proxy.protocol.ServerMessage;
import com.firegnom.proxy.protocol.ServerMessageDecoder;
import com.firegnom.proxy.protocol.ServerMessageEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final int TIMEOUT = 60;
	private ServerMessage message;
	private ResponseListener responseListener;
	
	public ClientInitializer(ServerMessage message, ResponseListener responseListener ) {
		this.message = message;
		this.responseListener = responseListener;
	}


	
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new ReadTimeoutHandler(TIMEOUT));
		p.addLast(new WriteTimeoutHandler(TIMEOUT));
		p.addLast(new ServerMessageEncoder());
		p.addLast(new ServerMessageDecoder());
		p.addLast(new ClientHandler(message,responseListener));
	}



	public ServerMessage getResponse() {
		// TODO Auto-generated method stub
		return null;
	}
}
