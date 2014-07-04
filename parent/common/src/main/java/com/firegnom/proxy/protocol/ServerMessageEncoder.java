package com.firegnom.proxy.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ServerMessageEncoder extends MessageToByteEncoder<ServerMessage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ServerMessage msg,
			ByteBuf out) throws Exception {
		//write number
		out.writeInt(msg.getNumber());
		
		//write message
		int size = 0;
		if (msg != null && msg.getMessage() != null) {
			size = msg.getMessage().length();
		}
		out.writeInt(size);
		for (int i = 0 ; i < size ;i ++){
			char charAt = msg.getMessage().charAt(i);
			out.writeChar(charAt);
		}
		
	}

}
