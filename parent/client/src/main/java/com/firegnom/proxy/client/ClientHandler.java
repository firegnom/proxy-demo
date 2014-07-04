package com.firegnom.proxy.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.firegnom.proxy.protocol.ServerMessage;

public class ClientHandler extends ChannelInboundHandlerAdapter {

	private final ServerMessage message;
	private ServerMessage response;
	private ResponseListener responseListener;

	private static final Logger LOG = LoggerFactory
			.getLogger(ClientHandler.class);

	/**
	 * Creates a client-side handler.
	 */
	public ClientHandler(ServerMessage message, ResponseListener responseListener) {
		this.message = message;
		this.responseListener = responseListener;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(message);
		LOG.info("Sent to server number : "+ message.getNumber() +" and message :"+ message.getMessage());
	}

	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ServerMessage m = (ServerMessage) msg;
		LOG.debug("Recieved from server number : " + m.getNumber()
				+ " and message :" + m.getMessage());
		responseListener.response(m);
		
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}
}
