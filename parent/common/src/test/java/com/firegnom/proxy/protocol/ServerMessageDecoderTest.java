package com.firegnom.proxy.protocol;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * The Class ServerMessageDecoderTest. is responsible for testing Server message
 * decoder , it is using Mockito framework to mock ByteBuf and interactions with
 * this object
 */
public class ServerMessageDecoderTest {

	/** The mocked context not used . */
	@Mock
	ChannelHandlerContext ctx;

	/** The mocked Byte buffer used i this test */
	@Mock
	ByteBuf in;

	/**
	 * Inits the mockito annotations on this object
	 */
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Test if there is no number in the buffer there should be no objects
	 * created in the object list
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testDecoderNoNumber() throws Exception {
		List<Object> out = new ArrayList<Object>();
		// prepare mocked object to return corect data in correct order
		when(in.readableBytes()).thenReturn(0);

		ServerMessageDecoder d = new ServerMessageDecoder();
		d.decode(ctx, in, out);

		assertEquals(0, out.size());

	}

	/**
	 * Test if there is number in the buffer but there is no length for the
	 * string there should be no objects created in the object list
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testDecoderNumberOnly() throws Exception {
		List<Object> out = new ArrayList<Object>();
		// prepare mocked object to return corect data in correct order
		when(in.readableBytes()).thenReturn(4, 0);

		ServerMessageDecoder d = new ServerMessageDecoder();
		d.decode(ctx, in, out);

		assertEquals(0, out.size());

	}

	/**
	 * Test decoder to get working message.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testDecoderMessage() throws Exception {
		List<Object> out = new ArrayList<Object>();

		// prepare mocked object to return corect data in correct order
		when(in.readableBytes()).thenReturn(4, 4);
		when(in.readInt()).thenReturn(4, 2);
		when(in.readChar()).thenReturn('a', 'b');

		ServerMessageDecoder d = new ServerMessageDecoder();
		d.decode(ctx, in, out);

		assertEquals(1, out.size());

		ServerMessage m = (ServerMessage) out.get(0);

		// check if number is correct
		assertEquals(4, m.getNumber());
		// check if message is correct
		assertEquals("ab", m.getMessage());
		// verify that read char was executed only twice
		verify(in, times(2)).readChar();

	}

	/**
	 * Test decoder to get working message.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testDecoderMessage0len() throws Exception {
		List<Object> out = new ArrayList<Object>();
		// prepare mocked object to return corect data in correct order
		when(in.readableBytes()).thenReturn(4, 4);
		when(in.readInt()).thenReturn(4, 0);

		ServerMessageDecoder d = new ServerMessageDecoder();
		d.decode(ctx, in, out);

		assertEquals(1, out.size());

		ServerMessage m = (ServerMessage) out.get(0);

		assertEquals(4, m.getNumber());
		assertEquals("", m.getMessage());
		// verify that read char was never executed
		verify(in, never()).readChar();

	}
}
