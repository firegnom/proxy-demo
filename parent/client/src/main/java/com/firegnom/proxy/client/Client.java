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

public class Client {
	private int serverPort;
	private String serverAddress = "localhost";

	public Client(int serverPort) {
		this.serverPort = serverPort;
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(Client.class);

	public void send(ServerMessage m, ResponseListener listener) throws Exception {

		// Configure the client.
		EventLoopGroup group = new NioEventLoopGroup();
		ClientInitializer clientInitializer = new ClientInitializer(m,listener);
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
	//TODO move this to separate class ClientRunner as it is not a vital part of the Client
	public static void main(String[] args) throws Exception {
		LOG.info("Initialising Client");
		Client c = new Client(49000); 
		
		c.send(new ServerMessage("test message", 123), new ResponseListener() {
			@Override
			public void response(ServerMessage m) {
				LOG.info("Recieved from server number : " + m.getNumber()
						+ " and message :" + m.getMessage());
				
			}
		});
		
		
	}

}
