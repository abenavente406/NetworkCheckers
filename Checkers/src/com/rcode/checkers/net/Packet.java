package com.rcode.checkers.net;

public abstract class Packet {

	public static enum PacketType {

		INVALID(-1), LOGIN(00), DISCONNECT(01), MOVE(03), MESSAGE(04);

		private int id;

		private PacketType(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}
	
	public byte packetId;
	
	/**
	 * Creates a packet with a specific id
	 * @param packetId
	 *   Packet id of the packet
	 */
	public Packet(int packetId) {
		this.packetId = (byte) packetId;
	}
	
	public abstract void writeData(Server server);
	
	public abstract void writeData(Client client);
	
	public abstract byte[] getData();
	
	public String readData(byte[] data) {
		return new String(data).trim();
	}
	
	public static PacketType lookUpPacket(String packetId) {
		try {
			return lookUpPacket(Integer.parseInt(packetId));
		} catch (NumberFormatException ex) {
			return PacketType.INVALID;
		}
	}
	
	public static PacketType lookUpPacket(int packetId) {
		for (PacketType p : PacketType.values()) {
			if (p.getId() == packetId)
				return p;
		}
		return PacketType.INVALID;
	}
	
}
