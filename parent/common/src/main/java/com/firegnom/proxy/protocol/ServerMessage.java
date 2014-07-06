package com.firegnom.proxy.protocol;

public class ServerMessage {
	private String message;
	private int number;

	public ServerMessage(String message, int number) {
		this.message = message;
		this.setNumber(number);
	}

	public ServerMessage() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
