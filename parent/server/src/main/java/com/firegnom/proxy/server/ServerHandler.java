package com.firegnom.proxy.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.firegnom.proxy.protocol.ServerMessage;

@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
	private static final Logger LOG = LoggerFactory
			.getLogger(ServerHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ServerMessage m = (ServerMessage) msg;
		LOG.info("Recieaved message :" + m.getMessage());
		// modify incoming message
		m.setMessage(m.getMessage() + " <SEEN BY SERVER>");
		m.setNumber(m.getNumber() + 321);
		// send it back
		ctx.writeAndFlush(m);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		LOG.warn("Unexpected exception from downstream.", cause);
		ctx.close();
	}

}
