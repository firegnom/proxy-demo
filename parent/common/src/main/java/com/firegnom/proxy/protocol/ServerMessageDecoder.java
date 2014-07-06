package com.firegnom.proxy.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ServerMessageDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		
		//decode number from buffer
		//check if there is number
		if (in.readableBytes() < 4) {
	        return;
	    }
		int number = in.readInt();
		
		//check if there is string length 
		if (in.readableBytes() < 4) {
	        return;
	    }
		
		//decode message from buffer
	    int len = in.readInt();
	    
	    StringBuffer b = new StringBuffer();
	    while (len > 0){	
	    	b.append(in.readChar());
	    	len--;
	    }
	    
	    out.add(new ServerMessage(b.toString(),number));
		
	}

}
