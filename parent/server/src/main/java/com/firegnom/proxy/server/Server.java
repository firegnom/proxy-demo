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
 * The Class Server is responsible for setting up a server which listens on tcp
 * socket specified as a parameter . Server is very simple it takes recieved
 * message and to number it adds 321 and to string it append " <SEEN BY SERVER>"
 * after that it sends response to the client with modified message
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
	 * Run.
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
					.handler(new LoggingHandler(LogLevel.INFO))
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

	
	/**
	 * The main method. starting server 
	 * 
	 * @param args
	 *            the args
	 * @throws Exception
	 *             the exception
	 */
	//TODO move this to separate class ServerRunner as it is not a vital part of the server  
	public static void main(String[] args) throws Exception {
		Server server = new Server(49001);
		server.run();
	}
}
