package com.firegnom.proxy;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ProxyOutboundHandler.
 */
public class ProxyOutboundHandler extends AbstractProxyHandler {

	/** The inbound channel. */
	private final Channel inboundChannel;

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory
			.getLogger(ProxyOutboundHandler.class);

	/**
	 * The Constructor.
	 *
	 * @param inboundChannel
	 *            the inbound channel
	 */
	public ProxyOutboundHandler(Channel inboundChannel) {
		this.inboundChannel = inboundChannel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.
	 * channel.ChannelHandlerContext)
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ctx.read();
		ctx.write(Unpooled.EMPTY_BUFFER);
	}

	/**
	 * Read from the outbound channel and forward that information back to
	 * inbound channel
	 * 
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel
	 *      .ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg) {
		inboundChannel.writeAndFlush(msg).addListener(
				new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture future) {
						if (future.isSuccess()) {
							ctx.channel().read();
						} else {
							future.channel().close();
						}
					}
				});
	}

	/**
	 * When Channel is no longer active it is time to flush and close inbound
	 * channel
	 * 
	 * 
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelInactive(io.netty
	 *      .channel.ChannelHandlerContext)
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		LOG.debug("Channel is no longer active, closing channnel");
		closeOnFlush(inboundChannel);
	}

}
