package com.firegnom.proxy;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

/**
 * The Class ProxyInitializer responsible for proxy channel initialization.
 */
public class ProxyInitializer extends ChannelInitializer<SocketChannel> {

	/** The remote host. */
	private final String remoteHost;

	/** The remote port. */
	private final int remotePort;

	/** The timeout for . */
	private final int timeout;

	/**
	 * The Constructor.
	 *
	 * @param remoteHost
	 *            the remote host
	 * @param remotePort
	 *            the remote port
	 * @param timeout
	 *            the timeout
	 */
	public ProxyInitializer(String remoteHost, int remotePort, int timeout) {
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
		this.timeout = timeout;
	}

	/**
	 * <p>
	 * This function initialize proxy channel with:
	 * </p>
	 * <ul>
	 * <li> {@link com.firegnom.proxy.ProxyInboundHandler}</li>
	 * <li> {@link io.netty.handler.timeout.ReadTimeoutHandler}</li>
	 * <li> {@link io.netty.handler.timeout.WriteTimeoutHandler}</li>
	 * </ul>
	 * <p>
	 * Both timeouts are configured by {@link com.firegnom.proxy.ProxyConfig}
	 * </p>
	 *
	 * @param ch
	 *            the ch
	 * @see io.netty.channel.ChannelInitializer#initChannel(io.netty.channel.Channel)
	 */
	@Override
	public void initChannel(SocketChannel ch) {
		ch.pipeline().addLast(new ReadTimeoutHandler(timeout))
				.addLast(new WriteTimeoutHandler(timeout))
				.addLast(new ProxyInboundHandler(remoteHost, remotePort));
	}
}
