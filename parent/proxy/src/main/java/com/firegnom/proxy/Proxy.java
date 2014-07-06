package com.firegnom.proxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Proxy is an implementation of proxy server responsible for creating
 * a bridge between client and remote server.
 */
public class Proxy {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(Proxy.class);

	/** The config. */
	private final ProxyConfig config;

	/**
	 * The Constructor configures Proxy server with default properties
	 */
	public Proxy() {
		LOG.info("Initialising proxyserver");
		config = new ProxyConfig();
	}

	/**
	 * The Constructor configures Proxy server with properties stored in file
	 * passed as a parameter
	 *
	 * @param configFile
	 *            the config file
	 */
	public Proxy(String configFile) {
		LOG.info("Initialising proxyserver");
		config = new ProxyConfig(configFile);
	}

	/**
	 * Run function is responsible for starting a proxy server , it creates a
	 * thread pools and binds to specified port
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void run() throws Exception {
		LOG.info("Proxying conections on port :" + config.getLocalPort()
				+ " to remote " + config.getRemoteHost() + ":"
				+ config.getRemotePort());

		// Configure the bootstrap.
		EventLoopGroup bossGroup = new NioEventLoopGroup(
				config.getThreadsBoss());
		EventLoopGroup workerGroup = new NioEventLoopGroup(
				config.getThreadsWorker());
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					// Create a non blocking IO channel
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(
							new ProxyInitializer(config.getRemoteHost(), config
									.getRemotePort(), config.getTimeout()))
					.childOption(ChannelOption.AUTO_READ, false)
					// bind to local port and listen for new connections
					.bind(config.getLocalPort()).sync().channel().closeFuture()
					.sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

}
