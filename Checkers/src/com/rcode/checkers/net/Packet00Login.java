package com.rcode.checkers.net;

public class Packet00Login extends Packet {

	String username;
	
	public Packet00Login(byte[] data) {
		super(00);
		this.username = readData(data);
	}

	@Override
	public void writeData(Server server) {
		server.sendDataToAll(getData());
	}

	@Override
	public void writeData(Client client) {
		client.sendData(getData());
	}

	@Override
	public byte[] getData() {
		return null;
	}

}
