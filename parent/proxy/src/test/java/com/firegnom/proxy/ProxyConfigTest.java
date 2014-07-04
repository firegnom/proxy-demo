package com.firegnom.proxy;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProxyConfigTest {

	@Test
	public void testProxyConfig() {
		try{
		ProxyConfig c = new ProxyConfig();
		}catch (Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testProxyConfigString() {
		try{
			ProxyConfig c = new ProxyConfig("proxy_test.properties");
			}catch (Exception e){
				fail(e.getMessage());
			}
	}

	@Test
	public void testGetLocalPort() {
		ProxyConfig c = new ProxyConfig("proxy_test.properties");
		assertEquals(1111,c.getLocalPort());
	}
	@Test
	public void testGetLocalPortDefault() {
		ProxyConfig c = new ProxyConfig("proxy_test_empty.properties");
		assertEquals(49000,c.getLocalPort());
	}

	@Test
	public void testGetRemotePort() {
		ProxyConfig c = new ProxyConfig("proxy_test.properties");
		assertEquals(2222,c.getRemotePort());
	}
	@Test
	public void testGetRemotePortDefault() {
		ProxyConfig c = new ProxyConfig("proxy_test_empty.properties");
		assertEquals(49001,c.getRemotePort());
	}

	@Test
	public void testGetRemoteHost() {
		ProxyConfig c = new ProxyConfig("proxy_test.properties");
		assertEquals("remote",c.getRemoteHost());
	}
	@Test
	public void testGetRemoteHostDefaults() {
		ProxyConfig c = new ProxyConfig("proxy_test_empty.properties");
		assertEquals("localhost",c.getRemoteHost());
	}

	@Test
	public void testGetTimeout() {
		ProxyConfig c = new ProxyConfig("proxy_test.properties");
		assertEquals(3333,c.getTimeout());
	}
	@Test
	public void testGetTimeoutDefaults() {
		ProxyConfig c = new ProxyConfig("proxy_test_empty.properties");
		assertEquals(60,c.getTimeout());
	}

	@Test
	public void testGetThreadsBoss() {
		ProxyConfig c = new ProxyConfig("proxy_test.properties");
		assertEquals(4444,c.getThreadsBoss());
	}
	@Test
	public void testGetThreadsBossDefaults() {
		ProxyConfig c = new ProxyConfig("proxy_test_empty.properties");
		assertEquals(1,c.getThreadsBoss());
	}

	@Test
	public void testGetThreadsWorker() {
		ProxyConfig c = new ProxyConfig("proxy_test.properties");
		assertEquals(5555,c.getThreadsWorker());
	}
	@Test
	public void testGetThreadsWorkerDefaults() {
		ProxyConfig c = new ProxyConfig("proxy_test_empty.properties");
		assertEquals(16,c.getThreadsWorker());
	}

}
