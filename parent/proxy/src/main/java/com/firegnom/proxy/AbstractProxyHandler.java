package com.firegnom.proxy;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * The Class AbstractProxyHandler.
 *
 */
public abstract class AbstractProxyHandler extends ChannelInboundHandlerAdapter {

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
}