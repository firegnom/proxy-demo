package com.firegnom.proxy.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.firegnom.proxy.protocol.ServerMessage;

/**
 * The Class Client.
 */
public class Client {

	/** The server port. */
	private int serverPort;

	/** The server address. */
	private String serverAddress = "localhost";

	/**
	 * The Constructor.
	 *
	 * @param serverPort
	 *            the server port
	 * @param serverAddress
	 *            the server address
	 */
	public Client(int serverPort, String serverAddress) {
		this.serverPort = serverPort;
		this.serverAddress = serverAddress;
	}

	/**
	 * The Constructor takes only one parameter and uses &quot;localhost&quot;
	 * as a .
	 *
	 * @param serverPort
	 *            the server port
	 */
	public Client(int serverPort) {
		this.serverPort = serverPort;
	}

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(Client.class);

	/**
	 * Send.
	 *
	 * @param m
	 *            the m
	 * @param listener
	 *            the listener
	 * @throws Exception
	 *             the exception
	 */
	public void send(ServerMessage m, ResponseListener listener)
			throws Exception {
		LOG.debug("Sending a message to server");
		// Configure the client.
		// Setup Thread factory for the client
		EventLoopGroup group = new NioEventLoopGroup();
		// setup client initialiser pass listener which will get response from
		// the server
		ClientInitializer clientInitializer = new ClientInitializer(m, listener);
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(clientInitializer);
			// Start the client.
			ChannelFuture f = b.connect(serverAddress, serverPort).sync();
			// Wait until the connection is closed.
			f.channel().closeFuture().sync();
		} finally {
			// Shut down the event loop to terminate all threads.
			group.shutdownGracefully();
		}

	}

}
