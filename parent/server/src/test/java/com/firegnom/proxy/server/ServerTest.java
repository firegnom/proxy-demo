package com.firegnom.proxy.server;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import org.junit.Test;



public class ServerTest {
	private byte [] message = javax.xml.bind.DatatypeConverter.parseHexBinary("000000ff000000040074006500730074");
	
	private byte [] resp = new byte[255];
	class ServerThread extends Thread {
		@Override
		public void run() {
			Server s = new Server(49001);
			try {
				s.run();
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			super.run();
		}
	}

	
	
	@Test
	public void testServer() throws Exception{
		ServerThread server = new ServerThread();
		server.start();
		Thread.sleep(1000);
		
		
		System.out.println("j");
		int len = message.length,start = 0;
		DataOutputStream out = null;
		DataInputStream in = null;
		
		
		Socket socket = new Socket("127.0.0.1", 49001);
		
	    out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	    in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

	    //out.writeInt(len);
		if (len > 0) {
			out.write(message, start, len);
		}
		out.flush();

		System.out.println("jj");
		
		in.read(resp);
		
		String s = new String(resp,"UTF-16");
		
		System.out.println(s);
		
		assertTrue(s.contains("test <SEEN BY SERVER>"));
		
		
		out.close();
	    in.close();
		socket.close();
		
	}

}
