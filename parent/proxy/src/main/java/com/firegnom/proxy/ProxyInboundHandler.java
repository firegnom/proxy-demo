package com.firegnom.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;

/**
 * The Class ProxyInboundHandler.
 */
public class ProxyInboundHandler extends AbstractProxyHandler {

	/** The remote host. */
	private final String remoteHost;

	/** The remote port. */
	private final int remotePort;

	/** The outbound channel. */
	private volatile Channel outboundChannel;

	/**
	 * The Constructor.
	 *
	 * @param remoteHost
	 *            the remote host
	 * @param remotePort
	 *            the remote port
	 */
	public ProxyInboundHandler(String remoteHost, int remotePort) {
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
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
		final Channel inboundChannel = ctx.channel();

		// Start the connection attempt.
		Bootstrap b = new Bootstrap();
		b.group(inboundChannel.eventLoop()).channel(ctx.channel().getClass())
				.handler(new ProxyOutboundHandler(inboundChannel))
				.option(ChannelOption.AUTO_READ, false);
		ChannelFuture f = b.connect(remoteHost, remotePort);
		outboundChannel = f.channel();
		f.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) {
				if (future.isSuccess()) {
					// connection complete start to read first data
					inboundChannel.read();
				} else {
					// Close the connection if the connection attempt has
					// failed.
					inboundChannel.close();
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel
	 * .ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg) {
		if (outboundChannel.isActive()) {
			outboundChannel.writeAndFlush(msg).addListener(
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
	}

	/**
	 * When Inbound Channel is no longer active it is time to flush and close
	 * outbound Channel
	 * 
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelInactive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		if (outboundChannel != null) {
			closeOnFlush(outboundChannel);
		}
	}

}