package com.firegnom.proxy.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.firegnom.proxy.protocol.ServerMessageDecoder;
import com.firegnom.proxy.protocol.ServerMessageEncoder;

/**
 * The Server class is responsible for setting up a server which listens on TCP
 * socket specified as a parameter in constructor . Server is very simple it
 * listens for the message from the client , after that it adds 321 to number in
 * the message and to append's &quot; &lt;SEEN BY SERVER&gt;&quot; to String in
 * the message after that it sends modified response back to the client.
 */
public class Server {

	/** The port. */
	private final int port;

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(Server.class);

	/** The Constant TIMEOUT. */
	private static final int TIMEOUT = 60;

	/**
	 * The Constructor.
	 *
	 * @param port
	 *            the port
	 */
	public Server(int port) {
		this.port = port;
	}

	/**
	 * Run is the main function of the server responsible for creating two
	 * thread pools and binding to port specified during object construction
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void run() throws Exception {
		LOG.info("Server has been started on port: " + port);
		// Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootStrap = new ServerBootstrap();
			bootStrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100)
					// This handler is used for debugging purposes
					.handler(new LoggingHandler(LogLevel.DEBUG))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)
								throws Exception {
							ChannelPipeline p = ch.pipeline();
							p.addLast(new ReadTimeoutHandler(TIMEOUT));
							p.addLast(new WriteTimeoutHandler(TIMEOUT));
							p.addLast(new ServerMessageDecoder());
							p.addLast(new ServerMessageEncoder());
							p.addLast(new ServerHandler());
						}
					});

			// Start the server.
			ChannelFuture f = bootStrap.bind(port).sync();

			// Wait until the server socket is closed.
			f.channel().closeFuture().sync();
		} finally {
			// Shut down all event loops to terminate all threads.
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			LOG.info("Server closed sucessfully");
		}

	}

}
