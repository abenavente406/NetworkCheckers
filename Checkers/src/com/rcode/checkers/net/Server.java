package com.rcode.checkers.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import com.rcode.checkers.Player;

public class Server extends Thread {

	private DatagramSocket socket;
	private List<Player> players = new ArrayList<Player>();
	private boolean running = false;
	
	public Server() {
		try {
			this.socket = new DatagramSocket(9090);
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
	}
	
	public Server(int port) {
		try {
			this.socket = new DatagramSocket(port);
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		running = true;
		while (running) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parsePacket(data, packet.getAddress(), packet.getPort());
		}
	}
	
	private void parsePacket(byte[] data, InetAddress ipAddress, int port) {
		
	}
	
	public void sendData(byte[] data) {
		
	}
	
	public void sendDataToAll(byte[] data) {
		
	}
	
	public void broadcastMessage(String message) {
		sendDataToAll(message.getBytes());
	}
	
	public void addConnection(Player player) {
		
	}
	
	public void removeConnection(String username) {
		
	}
	
	public void removeConnection(Player player) {
		
	}
	
	public int getPlayerIndex(String username) {
		return 0;
	}
	
	public Player getPlayer(String username) {
		return null;
	}
}
