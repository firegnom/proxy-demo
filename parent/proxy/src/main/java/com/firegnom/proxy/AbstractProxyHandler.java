package com.firegnom.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * The Class AbstractProxyHandler.
 *
 */
public abstract class AbstractProxyHandler extends ChannelInboundHandlerAdapter {

	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractProxyHandler.class);

	/**
	 * Helper function responsible for closing specified channel ,after all
	 * queued write requests are flushed.
	 *
	 * @param channel
	 *            the channel
	 */
	void closeOnFlush(Channel channel) {
		if (channel.isActive()) {
			channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(
					ChannelFutureListener.CLOSE);
		}
	}

	/**
	 * This function is executed when throwable is caught in the Channel , in
	 * this case exception is logged and channel is closed.
	 * 
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext,
	 *      java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		LOG.warn("Exception caught in the Channel", cause);
		closeOnFlush(ctx.channel());
	}
}