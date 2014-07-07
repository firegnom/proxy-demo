package com.firegnom.proxy.protocol;

/**
 * The Class ServerMessage stores message which is sent to the server and from
 * the server.
 */
public class ServerMessage {

	/** The message. */
	private String message;

	/** The number. */
	private int number;

	/**
	 * The Constructor.
	 *
	 * @param message
	 *            the message
	 * @param number
	 *            the number
	 */
	public ServerMessage(String message, int number) {
		this.message = message;
		this.setNumber(number);
	}

	/**
	 * The Constructor.
	 */
	public ServerMessage() {
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Sets the number.
	 *
	 * @param number
	 *            the number
	 */
	public void setNumber(int number) {
		this.number = number;
	}

}
